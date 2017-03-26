package org.spice3d.variation.util;



import java.text.SimpleDateFormat;

public class AppConstants {
	
	public static final String PATH_SEPARATOR = "/";
	public static final String PERIOD = ".";
	public static final String JSON = PERIOD + "json";
	public static final String XML = PERIOD + "xml";

	public static final String DEFAULT_RESPONSE_FORMAT = "json";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static final String REST_HOME_PAGE = "https://127.0.0.1:8080/variation-service/";
	public static final String REST_API_DOCS = "https://127.0.0.1:8080/variation-service/apidocs/index.html";
	
	public static final int GENERIC_APP_ERROR_CODE = 1000;
	public static final int BAD_REQUEST_ERROR_CODE = 1001;
	public static final int NOT_FOUND_ERROR_CODE = 1002;

	public static final String SERVLET_NAME = "/variation-service";

	public static final String PATH_PING = PATH_SEPARATOR + "ping";
	public static final String PATH_ALL = PATH_SEPARATOR + "all";


	public static final String VARIATION = "variation";
	
	public static final String PATH_VARIATION = PATH_SEPARATOR + VARIATION;


}
