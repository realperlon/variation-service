package org.spice3d.variation.filter.request;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/** Provides some customized logging
 * 
 *
 */
public class CustomLoggingFeature extends LoggingFeature implements ContainerRequestFilter, ContainerResponseFilter 
{
	static Logger logger = LoggerFactory.getLogger(CustomLoggingFeature.class);
	
    @Override
    public void filter(ContainerRequestContext requestContext)  throws IOException 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("User: ").append(requestContext.getSecurityContext().getUserPrincipal() == null ? "unknown"
                        : requestContext.getSecurityContext().getUserPrincipal());
        sb.append(" - Path: ").append(requestContext.getUriInfo().getPath());
        sb.append(" - Header: ").append(requestContext.getHeaders());
        sb.append(" - Entity: ").append(getEntityBody(requestContext));
        logger.debug("HTTP REQUEST : " + sb.toString());
    }
 
    private String getEntityBody(ContainerRequestContext requestContext) 
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();
         
        final StringBuilder b = new StringBuilder();
        try
        {
            ReaderWriter.writeTo(in, out);
 
            byte[] requestEntity = out.toByteArray();
            if (requestEntity.length == 0)
            {
                b.append("").append("\n");
            }
            else
            {
                b.append(new String(requestEntity)).append("\n");
            }
            requestContext.setEntityStream( new ByteArrayInputStream(requestEntity) );
 
        } catch (IOException ex) {
            //ignore logging error
        }
        return b.toString();
    }
 
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Header: ").append(responseContext.getHeaders());
        sb.append(" - Entity: ").append(responseContext.getEntity());
        
        logger.debug("HTTP RESPONSE : " + sb.toString());
    }
}
