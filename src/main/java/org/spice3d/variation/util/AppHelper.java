package org.spice3d.variation.util;


import io.swagger.jaxrs.config.BeanConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spice3d.variation.response.ResponseMessage;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class AppHelper {

	private static final Logger log = LoggerFactory.getLogger(AppHelper.class);
	
	
	
	
	
	public static byte[] getByteArrayFromFile(String fileName) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        // From ClassLoader, all paths are "absolute" already - there's no context
        // from which they could be relative. Therefore we don't need a leading slash.
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream(fileName);

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
        	buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }
    
    public static byte[] getByteArrayFromSmallFile(String fileName) throws IOException {
    	byte [] bytes = null;

    	// From ClassLoader, all paths are "absolute" already - there's no context
    	// from which they could be relative. Therefore we don't need a leading slash.
    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	InputStream is = loader.getResourceAsStream(fileName);

    	bytes = IOUtils.toByteArray(is);

        return bytes;
        
    }
    
	public static String getResponseMediaType(String format, HttpHeaders headers){
		//default is json
		String mediaType = MediaType.APPLICATION_JSON;
		//String acceptType = headers.getAcceptableMediaTypes().get(0).toString(); 
		
		if(!StringUtils.isBlank(format) && format.equalsIgnoreCase("xml")){
			mediaType = MediaType.APPLICATION_XML;						
		}
		log.info("returning results in format: " + mediaType);
		return mediaType;
	}
	
	public static Date getTuesdayMidnight(){
		Calendar c= Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.add(Calendar.DAY_OF_MONTH, 7);
		return c.getTime();
	}
	
	public static Date calcNextTuesdayMidnight() {
		LocalDate today = LocalDate.now();
		LocalDateTime tuesdayMidnight = LocalDateTime.of(today.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)), LocalTime.MIDNIGHT);
		
		return Date.from(tuesdayMidnight.atZone(ZoneId.of("UTC")).toInstant());

	}
	
	public static Date getHourFromNow() {
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
	    return cal.getTime(); // returns new date object, one hour in the future

	}
	
	public static Date getSevenDaysFromNow() {
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_MONTH, 7); // adds 7 days
	    return cal.getTime(); // returns new date object, seven days in the future
	}
	
	public static BeanConfig getSwaggerBeanConfig(){
		Properties pros = AppProperties.getAppProperties();
    	BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(pros.getProperty("project.version", "NA"));
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setBasePath(AppConstants.SERVLET_NAME +"/rest/");
        beanConfig.setResourcePackage("org.spice3d.variation.rest");
        beanConfig.setDescription( "List of services currently provided" );
        beanConfig.setTitle( "RESTful Services API" );
        beanConfig.setScan(true);
        return beanConfig;
    }

	public static <K> Response responseForBadUserRequest(HttpHeaders headers, ResponseMessage responseMsg, String format, String message) {
		Response.ResponseBuilder responseBuilder;
		responseMsg.setCount(0);
		responseMsg.setCode(Response.Status.BAD_REQUEST.getStatusCode());
		responseMsg.setMessage(message);
		responseMsg.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
		responseMsg.setLink(AppConstants.REST_API_DOCS);
		
		responseBuilder =  Response
		.status(Response.Status.BAD_REQUEST)
		.type(AppHelper.getResponseMediaType(format, headers))
		.entity(responseMsg);
		return responseBuilder.build();
	}

	
	public static List<String> getListFromString(String stringToSplit, String regexStr){
		List<String> listOfStrings = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(stringToSplit) && StringUtils.isNotBlank(regexStr)){
	    	String[] stringArray = stringToSplit.split(regexStr);
	    	for(String str : stringArray){
	    		if(StringUtils.isNotBlank(str)){
	    			listOfStrings.add(str.trim());
	    		}
	    	}
	        
		}
		return listOfStrings;
	}

}

