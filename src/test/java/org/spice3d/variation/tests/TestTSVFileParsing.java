package org.spice3d.variation.tests;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;
import org.spice3d.variation.dao.InMemoryVariationProvider;
import org.spice3d.variation.model.VariationRecord;
import org.spice3d.variation.parsers.FlatFileParserFacade;


public class TestTSVFileParsing {

	
	public static final int TOTAL_NR_RECORDS = 398890;
	
	@Test
	public void testTSVLineParsing() {
		
		InputStream inStream = TestTSVFileParsing.class.getClassLoader().getResourceAsStream("line.tsv");
		
		Scanner s = new java.util.Scanner(inStream);
		
		String line = s.nextLine();
		
		s.close();
		
		VariationRecord record = VariationRecord.fromTSV(line);
		
		assertEquals(record.toTSV(),line);
		
	}
	
	@Test
	public void testFullFile() throws Exception{
		
		//  makes sure we have the flat file installed locally
		InMemoryVariationProvider.checkSetup();
		
		// this tests the parsing independetly of the InMemoryVariationProvider cache
		// InMemoryVariationProvider gets tested elsewhere
		assertEquals(TOTAL_NR_RECORDS,FlatFileParserFacade.parseTSVFile().size());
	
	}

}
