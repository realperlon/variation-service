package org.spice3d.variation.tests;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.spice3d.variation.dao.InMemoryVariationProvider;
import org.spice3d.variation.dao.VariationProvider;


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
	}

	
}
