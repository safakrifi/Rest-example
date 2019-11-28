package com.example.springbootrestserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServletInitializer extends org.springframework.boot.web.support.SpringBootServletInitializer {

	   private static final Logger logger = LoggerFactory.getLogger(ServletInitializer.class);

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.info("Spring boot Servelet Iniializer source :Demo Application");
		return application.sources(DemoApplication.class);
	}

}
