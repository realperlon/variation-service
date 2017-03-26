package org.spice3d.variation.servlet;


import java.util.Properties;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.SelectableEntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.spice3d.variation.dao.InMemoryVariationProvider;
import org.spice3d.variation.filter.request.CustomLoggingFeature;
import org.spice3d.variation.filter.response.CORSResponseFilter;
import org.spice3d.variation.filter.response.CharsetResponseFilter;
import org.spice3d.variation.interceptor.GZIPWriterInterceptor;
import org.spice3d.variation.provider.CustomJAXBContextProvider;
import org.spice3d.variation.provider.ObjectJSONMessageBodyWriter;
import org.spice3d.variation.provider.ObjectXMLMessageBodyWriter;
import org.spice3d.variation.util.AppHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@ApplicationPath("/")
public class CustomApplication extends ResourceConfig
{
    private static final Logger logger = LoggerFactory.getLogger(CustomApplication.class);
    private static final String[] packagesToScan = {"org.spice3d.variation.rest","org.spice3d.variation.filter","org.spice3d.variation.exception.mapper"};
    private String SELECTABLE_ENTITY_FILTER = "select";

    public CustomApplication() throws Exception
    {

        super(MultiPartFeature.class);
        logger.info("Starting CustomApplication");

       

        packages(true, "org.spice3d.variation.rest;org.spice3d.variation.filter;org.spice3d.variation.exception.mapper;org.spice3d.variation.provider");
        register(CustomLoggingFeature.class);
        register(GZIPWriterInterceptor.class);
        register(CharsetResponseFilter.class);
        register(CORSResponseFilter.class);

        //use below to enable gzip for all responses.
        //For selective filtering based on annotations on a method/resource, use the @Compress annotation on the method/resource
        //EncodingFilter.enableFor(this, GZipEncoder.class);


        register(CustomJAXBContextProvider.class);
        register(ObjectXMLMessageBodyWriter.class);
        register(ObjectJSONMessageBodyWriter.class);

        register(SelectableEntityFilteringFeature.class);
        property(SelectableEntityFilteringFeature.QUERY_PARAM_NAME, SELECTABLE_ENTITY_FILTER);

        //in Jersey WADL generation is enabled by default, but we don't
        //want to expose too much information about our apis.
        //therefore we want to disable wadl (http://localhost:8080/rest/application.wadl should return http 404)
        //see https://jersey.java.net/nonav/documentation/latest/user-guide.html#d0e9020 for details

        // set to true to DISABLE wadl (currently enabled)
        property("jersey.config.server.wadl.disableWadl", false);

        //Hooking up Swagger-Core
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);

        // MVC.
        //property(MvcFeature.TEMPLATE_BASE_PATH, "/rest/");
        register(JspMvcFeature.class);


        //Configure and Initialize Swagger
        BeanConfig beanConfig = AppHelper.getSwaggerBeanConfig();
        
        // seed the internal cache
        InMemoryVariationProvider.getInstance();
        
        logger.info("Started Web Application");
    }

   
}
