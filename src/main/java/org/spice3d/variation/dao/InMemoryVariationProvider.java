package org.spice3d.variation.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spice3d.variation.model.VariationRecord;
import org.spice3d.variation.parsers.FlatFileParserFacade;
import org.spice3d.variation.util.AppProperties;

/** A basic implementation of a variation provider that keeps all data in memory.
 * To minimize memory usage, this class is a Singleton.
 * 
 * It contains an internal map that tracks all variations by gene name, for faster lookup.
 * 
 * 
 * @author andreas
 *
 */
public class InMemoryVariationProvider implements VariationProvider{

	static Logger logger = LogManager.getLogger(InMemoryVariationProvider.class);

	static VariationProvider instance =null ;

	Map<String,List<VariationRecord>> cache = new ConcurrentHashMap<>();


	// in case we want to track that we are busy with initiating this
	static AtomicBoolean initiating = new AtomicBoolean(false);

	private InMemoryVariationProvider (){
		try {
			checkSetup();
			initCacheInMemory();
		} catch (Exception e){
			logger.error("Could not init in memory cache!" , e);
		}
	}

	public static void checkSetup() throws FileNotFoundException, IOException {

		Properties props = AppProperties.getAppProperties();

		String filePath = props.getProperty("local.file.path");

		Path path =  Paths.get(filePath);

		if ( Files.exists(path)){
			logger.info("Found variation file at " + path);
		} else {

			String remoteFileLocation = props.getProperty("remote.file.url");

			URL remoteURL = new URL(remoteFileLocation);

			logger.info("Local file " + path + " does not exist. Will download from " + remoteFileLocation);

			downloadZipFile(remoteURL,path);
		}

	}

	private static void downloadZipFile(URL remoteURL, Path path) throws IOException, FileNotFoundException {

		ReadableByteChannel rbc = Channels.newChannel(remoteURL.openStream());
		FileOutputStream fos = new FileOutputStream(path.toFile());
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

	private void initCacheInMemory() throws IOException {

		List<VariationRecord> records = FlatFileParserFacade.parseTSVFile();

		records.forEach(e->{

			String geneName = e.getGene().toUpperCase();

			List<VariationRecord> knownRecords = cache.get(geneName);
			if ( cache.get(geneName) == null){
				knownRecords = new ArrayList<VariationRecord>();
				cache.put(geneName,knownRecords);				
			}
			knownRecords.add(e);
		});
		logger.info("got variations for " + cache.size() + " genes.");

	}



	/** Get the singleton that contains the data in memory.
	 * 	- Uses lazy initiation to only generate if needed.
	 *  - Makes sure access is synchronized, since startup is slow and
	 *  multiple threads could access is concurrently. If it would not be
	 *  synchronized, the flat-file could get parsed multiple times.
	 *  
	 * @return
	 */
	public static synchronized VariationProvider getInstance(){


		if (instance == null){
			initiating.set(true);
			instance = new InMemoryVariationProvider();
			initiating.set(false);
		}

		return instance;
	}

	
	/** Suggests possible gene names for the provided user input.
	 * All Gene names are stored as all-upper case to avoid possible confusion around
	 * case-sensitivity.
	 * 
	 * @param partialGeneName
	 * @return
	 */
	public List<String> suggestGeneNames(String partialGeneName) {
		
		
		final String upperCaseName = partialGeneName.toUpperCase();
				
		Set<String> knownGenes = cache.keySet();

		List<String> suggestions = Collections.synchronizedList(new ArrayList<String>());

		knownGenes.parallelStream().forEach(e->{
			if ( e.startsWith(upperCaseName)){
				if ( ! suggestions.contains(e))
					suggestions.add(e);
			}
		});

		return suggestions;

	}

	
	public List<VariationRecord> getVariantsForGeneName(String geneName) {
		return cache.get(geneName);
	}

	@Override
	public Set<String> getSupportedGeneNames() {
		return cache.keySet();
	}
	



}
