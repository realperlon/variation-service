package org.spice3d.variation.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.Test;
import org.spice3d.variation.dao.InMemoryVariationProvider;
import org.spice3d.variation.dao.VariationProvider;
import org.spice3d.variation.util.AppProperties;
import static org.junit.Assert.assertTrue;
public class TestSetup {

	
	/** A simple test that makes sure that the local variation file 
	 * is installed at the correct location.
	 * 
	 */
	 @Test
	public void testInstallation(){
		@SuppressWarnings("unused")
		VariationProvider prov = InMemoryVariationProvider.getInstance();
		
		Properties props = AppProperties.getAppProperties();
		
		String filePath = props.getProperty("local.file.path");

		Path path =  Paths.get(filePath);
		
		assertTrue(Files.exists(path));
		
	}
}
