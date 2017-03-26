package org.spice3d.variation.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spice3d.variation.model.VariationRecord;
import org.spice3d.variation.util.AppProperties;


/** A simple Facade that makes it easier to get the variation records out of the flat file.
 * 
 * @author andreas
 *
 */
public class FlatFileParserFacade {
	static Logger logger = LogManager.getLogger(FlatFileParserFacade.class);
	
	public static List<VariationRecord> parseTSVFile() throws IOException{
		
		long timeS = System.currentTimeMillis();
		Properties props = AppProperties.getAppProperties();
		
		String filePath = props.getProperty("local.file.path");

		Path path =  Paths.get(filePath);
				
		VariationFileParser parser = new VariationFileParser();
		
		SimpleVariationConsumer consumer = new SimpleVariationConsumer();
		parser.registerNewConsumer(consumer);
		
		ZipFile zipFile = new ZipFile(path.toAbsolutePath().toString());

	    Enumeration<? extends ZipEntry> entries = zipFile.entries();

	    while(entries.hasMoreElements()){
	        ZipEntry entry = entries.nextElement();
	        InputStream inStream = zipFile.getInputStream(entry);
	        
			parser.parse(inStream);
	    }
	    
	    zipFile.close();
	    
	    long timeE = System.currentTimeMillis();
	    
	    logger.info("time to parse variation tsv file:" + (timeE - timeS) + "ms.");
	    
	    return consumer.getAllVariationRecords();
	}
}
