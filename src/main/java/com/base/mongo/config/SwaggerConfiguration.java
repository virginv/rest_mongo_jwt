package com.base.mongo.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * This class will enable the swagger configuration during application startup.
 *
 * @author vleon
 */
@Configuration
@EnableSwagger2
@Profile("swagger")
public class SwaggerConfiguration {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfiguration.class);

    /**
     * It is a builder which acts as a primary interface in swagger-springmvc framework. It returns below things.
     * HOST:PORT/v2/api-docs
     * HOST:PORT/swagger-ui.html
     * @return configuracion Swagger Springfox
     */
    @Bean
    public Docket swaggerSpringfoxApiDocket(@Value("${spring.application.name}") String appName, 
								    		@Value("${info.project.description}") String appDescripcion,
								    		@Value("${info.project.version}") String appVersion,
								    		@Value("${management.context-path}") String managementContextPath) {
    	LOGGER.debug("Iniciando Swagger...");
        StopWatch watch = new StopWatch();
        watch.start();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
	        .apiInfo(new ApiInfoBuilder()
	        	    .title(appName)
	        	    .description(appDescripcion)
	        	    .version(appVersion)
	        	    .build())
	        .select() // returns an instance of ApiSelectorBuilder, which provides a way to control the endpoints exposed by Swagger.
	        .apis(RequestHandlerSelectors.basePackage("com.base.mongo.controllers")) // provides RequestHandlerSelectors which specified basepackage to scan for all the controllers.
	        .paths(regex(managementContextPath + ".*"))  // provides the mapping endpoints of our API's.
	        .build();
        watch.stop();
        LOGGER.debug("Swagger iniciado en {} ms", watch.getTotalTimeMillis());
        return docket;
    }
    

}
