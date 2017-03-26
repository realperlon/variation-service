package org.spice3d.variation.provider;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class ObjectXMLMessageBodyWriter implements MessageBodyWriter<Object> {

    @Context
    protected Providers providers;
    private final Set<Class<?>> types;
    private static final String CHARSET = "charset";

    static Logger logger = LoggerFactory.getLogger(ObjectXMLMessageBodyWriter.class);

    public ObjectXMLMessageBodyWriter(){
        super();
        this.types = new HashSet<Class<?>>(Arrays.asList(CustomJAXBContextProvider.cTypes));
    }
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return (types.contains(type)) ? true : false;
    }

    @Override
    public long getSize(Object les, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
         // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return 0;
    }

    @Override
    public void writeTo(Object obj, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String,
            Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        try {

            JAXBContext context = getJAXBContext(type, mediaType);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);

            Map<String, String> mediaTypeParameters = mediaType.getParameters();
            if(mediaTypeParameters.containsKey(CHARSET)) {
                String charSet = mediaTypeParameters.get(CHARSET);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, charSet);
            }

            marshaller.marshal(obj, entityStream);

        } catch (Exception e) {
            logger.error("Cant write JAXBContext to output stream ", e.getMessage());
            logger.debug("cause: ", e);
        }
    }

    private JAXBContext getJAXBContext(Class<?> type, MediaType mediaType)
            throws JAXBException {

            ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class, mediaType);
            JAXBContext jaxbContext = null;

            if(resolver != null){
                jaxbContext = resolver.getContext(type);
                if(jaxbContext == null){
                    jaxbContext = (new CustomJAXBContextProvider()).getContext(type);
                }
                if(jaxbContext == null){
                    return JAXBContext.newInstance(type);
                }
            }

            return jaxbContext;
        }
}
    


