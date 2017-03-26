package org.spice3d.variation.provider;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spice3d.variation.model.VariationRecord;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.*;
 
//By default a JAX-RS implementation will bootstrap a JAXBContext from the parameters or return type from a service method. 
//You can control how the JAXBContext is created through the ContextResolver mechanism. 
//The ContextResolver allows you to control what JAXBContext is used with a particular class. 
//In this example we tell the JAX-RS implementation to use a MOXy JAXBContext in which the metadata has been supplied via XML

/** Expose our custom bean for representing variation records via JAXB
 * 
 */
@Provider
public class CustomJAXBContextProvider implements ContextResolver<JAXBContext> {

    private final String[] bindingFileNames = {"variationRecord.xml"};
    public static final Class<?>[] cTypes = {VariationRecord.class};



	static Logger logger = LoggerFactory.getLogger(CustomJAXBContextProvider.class);
	
    private JAXBContext context = null;

	private final Set<Class<?>> types; 

    //private final List<Object> fileList;
    private final List<Object> inputStreamList;    
    private Map<String, Object> props = new HashMap<String, Object>(1);
       
    public CustomJAXBContextProvider() {
        try {
        	
        	this.types = new HashSet<Class<?>>(Arrays.asList(cTypes)); 
        	inputStreamList = new ArrayList<Object>();        	
        	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        	
        	for(String filename : bindingFileNames){
        		InputStream inputStream = classLoader.getResourceAsStream(filename);
        		inputStreamList.add(inputStream);
        		
        	}
        	
            props.put(JAXBContextProperties.OXM_METADATA_SOURCE, inputStreamList);
            
            this.context = JAXBContextFactory.createContext(cTypes, props);
           
            
        } catch(JAXBException e) {
        	logger.error("Cant instantiate CustomJAXBContextProvider ", e.getMessage());
			logger.debug("cause: ", e);
            throw new RuntimeException(e);
        }
    }
 
    public JAXBContext getContext(Class<?> type) {
    	if(!types.contains(type)) {
            return null; // we don't support anything other than the listed types
        }
 
        if (context == null) {
            try {
                context = JAXBContext.newInstance(cTypes);
            } catch (JAXBException e) {
                // null will be returned which indicates that our custom provider won't/can't be used.
            	logger.error("Context for CustomJAXBContextProvider is null. It cant be used ", e.getMessage());
				logger.debug("cause: ", e);
            }
        }
 
        return context;
    }

}
