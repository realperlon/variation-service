package org.spice3d.variation.service;


import org.apache.commons.lang3.StringUtils;

import org.spice3d.variation.dao.InMemoryVariationProvider;
import org.spice3d.variation.dao.VariationProvider;
import org.spice3d.variation.model.VariationRecord;
import org.spice3d.variation.response.ResponseMessageSuggest;
import org.spice3d.variation.response.ResponseMessageVariation;
import org.spice3d.variation.util.AppConstants;
import org.spice3d.variation.util.AppHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.*;
import java.util.List;

public class VariationServiceImpl implements VariationService {


	static Logger logger = LoggerFactory.getLogger(VariationServiceImpl.class);


	public VariationServiceImpl() {
		super();




	}

	@Override
	public Response suggestGeneNames(UriInfo uriInfo, Request request, HttpHeaders headers, String partialGeneName)  throws Exception {

		long timeS = System.currentTimeMillis();

		ResponseMessageSuggest responseMsg = new ResponseMessageSuggest();

		Response.ResponseBuilder responseBuilder = null;
		long timeInMilliSeconds = System.currentTimeMillis();

		String format = AppConstants.DEFAULT_RESPONSE_FORMAT;

		format = uriInfo.getQueryParameters().getFirst("format");

		if(StringUtils.isBlank(format)){
			format = AppConstants.DEFAULT_RESPONSE_FORMAT;
		} else if(!format.equalsIgnoreCase("json") && !format.equalsIgnoreCase("xml")){
			return AppHelper.responseForBadUserRequest(
					headers, responseMsg, format, "Format: "+format + " is not a supported format for this service");
		}

		responseMsg.setCode(Response.Status.OK.getStatusCode());
		responseMsg.setMessage("possible gene names");
		responseMsg.setStatus(Response.Status.OK.getStatusCode());
		responseMsg.setLink("http://www.spice-3d.org/");

		VariationProvider prov = InMemoryVariationProvider.getInstance();
		
		List<String> possibleGeneNames = prov.suggestGeneNames(partialGeneName);
		
		responseMsg.setResults(possibleGeneNames);
		responseMsg.setCount(possibleGeneNames.size());
		String etag = AppConstants.VARIATION+"-"+timeInMilliSeconds;
				
		responseBuilder =  Response
				.status(Response.Status.OK)
				.type(AppHelper.getResponseMediaType(format, headers))
				.entity(responseMsg)
				.tag(etag);

		long timeE = System.currentTimeMillis();
		logger.info("time to build response: " + (timeE-timeS)+" ms."); 
		return responseBuilder.build();
	}


	@Override
	public Response getVariantsForGeneName(UriInfo uriInfo, Request request, HttpHeaders headers, String geneName)
			throws Exception {
		
		//make geneName uppercase to avoid case sensitivity issues		
		geneName = geneName.toUpperCase();
	
		long timeS = System.currentTimeMillis();

		ResponseMessageVariation responseMsg = new ResponseMessageVariation();

		Response.ResponseBuilder responseBuilder = null;
		long timeInMilliSeconds = System.currentTimeMillis();

		String format = AppConstants.DEFAULT_RESPONSE_FORMAT;

		format = uriInfo.getQueryParameters().getFirst("format");

		if(StringUtils.isBlank(format)){
			format = AppConstants.DEFAULT_RESPONSE_FORMAT;
		} else if(!format.equalsIgnoreCase("json") && !format.equalsIgnoreCase("xml")){
			return AppHelper.responseForBadUserRequest(
					headers, responseMsg, format, "Format: "+format + " is not a supported format for this service");
		}
		responseMsg.setCode(Response.Status.OK.getStatusCode());
		responseMsg.setMessage("available variation data");
		responseMsg.setStatus(Response.Status.OK.getStatusCode());
		responseMsg.setLink("http://www.spice-3d.org/");

		VariationProvider prov = InMemoryVariationProvider.getInstance();
		
		List<VariationRecord> variationData = prov.getVariantsForGeneName(geneName);
		
		responseMsg.setResults(variationData);
		if ( variationData != null)
			responseMsg.setCount(variationData.size());
		else 
			responseMsg.setCount(0);
		String etag = AppConstants.VARIATION+"-"+timeInMilliSeconds;
				
		responseBuilder =  Response
				.status(Response.Status.OK)
				.type(AppHelper.getResponseMediaType(format, headers))
				.entity(responseMsg)
				.tag(etag);

		long timeE = System.currentTimeMillis();
		logger.info("time to build response: " + (timeE-timeS)+" ms."); 
		

		return responseBuilder.build();
		
	}

}
