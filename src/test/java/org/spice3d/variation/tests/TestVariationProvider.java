package org.spice3d.variation.tests;

import static org.junit.Assert.*;


import org.junit.Test;
import org.spice3d.variation.dao.InMemoryVariationProvider;
import org.spice3d.variation.dao.VariationProvider;
import org.spice3d.variation.model.VariationRecord;


/** A test that makes sure the data access layer provides results as expected
 * 
 * @author andreas
 *
 */
public class TestVariationProvider {


	@Test
	public void testVariationProvider(){

		VariationProvider prov = InMemoryVariationProvider.getInstance();

		assertTrue(prov.suggestGeneNames("BRCA").size()>0);

		assertTrue(prov.suggestGeneNames("BRCA").contains("BRCA1"));

		assertTrue(prov.getVariantsForGeneName("BRCA1").size()>8000);
		
		assertTrue(prov.getSupportedGeneNames().size() >8240);
		
		int count = 0;
		
		String maxGene = "";
		int maxVariants = 0;
		
		for ( String geneName : prov.getSupportedGeneNames()){
			int nrVariants = prov.getVariantsForGeneName(geneName).size();
			
			if ( nrVariants > maxVariants){
				maxGene = geneName;
				maxVariants = nrVariants;
			}
			count += nrVariants;
			
		}
		
		assertEquals(count,TestTSVFileParsing.TOTAL_NR_RECORDS);
		
		assertTrue(maxGene.equals("BRCA2"));
		
		assertTrue(maxVariants > 12000);
		
	}

	
}
