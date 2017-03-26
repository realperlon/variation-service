package org.spice3d.variation.filter.response;


import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;

/**
 * 
 * Makes sure the returned charset is set correctly
 * 
 */

public class CharsetResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        MediaType type = response.getMediaType();
        if (type != null) {
            String contentType = type.toString();
            if (!contentType.contains("charset")) {
                contentType = contentType + ";charset=utf-8";
                response.getHeaders().putSingle("Content-Type", contentType);
            }
        }
    }
}


