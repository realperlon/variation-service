package org.spice3d.variation.filter.response;


import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/** Makes sure the Access-Control-Allow-Origin response header is set correctly.
 * (allowing access from everywhere)
 * 
 *
 */
public class CORSResponseFilter
implements ContainerResponseFilter {

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		/** we allow access from everywhere.
		 * 
		 */
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET");			
		headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
	}

}
