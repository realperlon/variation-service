package org.spice3d.variation.parsers;

import java.util.ArrayList;
import java.util.List;

import org.spice3d.variation.model.VariationRecord;

/** A simple class that loads all copies of variation data in memory and adds them to a list
 * 
 * @author andreas
 *
 */
public class SimpleVariationConsumer implements VariationConsumer{

	private List<VariationRecord> records ;
	
	public SimpleVariationConsumer(){
		records = new ArrayList<>();
	}
	
	@Override
	public void newVariationRecord(VariationRecord record) {
		records.add(record);
		
	}
	
	public List<VariationRecord> getAllVariationRecords(){
		return records;
	}

	
	
}
