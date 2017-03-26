package org.spice3d.variation.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.spice3d.variation.model.VariationRecord;

public class VariationFileParser {

	List<VariationConsumer> consumers = new ArrayList<>();
	
	public VariationFileParser(){
		
		
	}
	
	public void registerNewConsumer(VariationConsumer consumer){
		consumers.add(consumer);
	}
	
	
	
	public void parse(InputStream stream) throws IOException{
		
		
		// if no consumers, no need to do anything
		if ( consumers.size() < 1)
			return;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));

		// read over the header field. Would be more convenient 
		// to have a comment char at the beginning...
		br.readLine();
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			VariationRecord record =  VariationRecord.fromTSV(line);
			
			consumers.parallelStream().forEach(e->e.newVariationRecord(record));
			
		}

	}
	
}
