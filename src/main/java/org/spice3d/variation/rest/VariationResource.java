package org.spice3d.variation.rest;

import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.spice3d.variation.service.VariationService;
import org.spice3d.variation.service.VariationServiceImpl;
import org.spice3d.variation.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author tara
 * This class services all REST requests starting with /birds
 *
 */

@Path(AppConstants.PATH_VARIATION)
@Api(tags = {AppConstants.VARIATION})
public class VariationResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private VariationService variationService;
	static Logger logger = LoggerFactory.getLogger(VariationResource.class);

	public VariationResource() throws Exception {
		super();
		variationService = new VariationServiceImpl();		
	}
	
	
	@GET
	@Path(AppConstants.PATH_PING)	
	@Produces({ MediaType.TEXT_PLAIN })
	@ApiOperation(value = "Check variation service status", 
    response = String.class)
	public String servicePing() {
		logger.info("received ping on " + new Date().toString());
		return "RESTful Variation Service is running.";
	}


	@PermitAll
	@GET
	@Path("suggest/{partialName}")
	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
	@ApiOperation(value = "suggests full gene names for partial names", 
    notes = "Returns full gene names for user-provided text input",
    response = Response.class)
	
  
	public Response getSuggestedGeneNames (
			@Context UriInfo uriInfo,
			@Context Request request,
			@PathParam("partialName") String partialName,		
			@Context HttpHeaders headers) throws Exception {
		
		logger.info("getting auto-suggest for partialName: " + partialName);
		return variationService.suggestGeneNames(uriInfo, request, headers, partialName);		
	}

	@PermitAll
	@GET
	@Path("variants/{fullName}")
	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML })
	@ApiOperation(value = "get available variation data for a gene", 
    notes = "Returns all available variation data for user-provided text input",
    response = Response.class)
	
	public Response getVariationForGene (
			@Context UriInfo uriInfo,
			@Context Request request,
			@PathParam("fullName") String fullName,
			@Context HttpHeaders headers) throws Exception {
		
		logger.info("getting variation for full name: " + fullName);
		return variationService.getVariantsForGeneName(uriInfo, request, headers, fullName);		
	}

	


}
