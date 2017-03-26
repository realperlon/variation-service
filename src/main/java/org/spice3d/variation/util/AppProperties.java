package org.spice3d.variation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** A utility class to manage configurations for the application.
 * 
 * 
 * @author andreas
 *
 */
public class AppProperties {

	private static Properties appProperties = null;

	public static Properties getAppProperties(){
		if (appProperties == null){
			initProperties();
		}
		
		return appProperties;
	}

	private static void initProperties(){

		appProperties = new Properties();

		InputStream input = null;

		try {

			String filename = "variation.properties";
			input = AppProperties.class.getClassLoader().getResourceAsStream(filename);
			if(input==null){
				System.out.println("Sorry, unable to find " + filename);
				return;
			}

			//load a properties file from class path, inside static method
			appProperties.load(input);


		} catch (IOException ex) {
			ex.printStackTrace();
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}






}
