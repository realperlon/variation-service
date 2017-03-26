package org.spice3d.variation.service;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public interface VariationService {

	 public Response suggestGeneNames(UriInfo uriInfo, Request request, HttpHeaders headers, String partialName)  throws Exception;
	
	 public Response getVariantsForGeneName(UriInfo uriInfo, Request request, HttpHeaders headers, String fullName) throws Exception;
}
