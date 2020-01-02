package com.example.springbootrestserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//http://localhost:8080/v2/api-docs

//http://localhost:8080/swagger-ui.html
@SpringBootApplication
@ComponentScan("com.example")//to scan repository files
@EntityScan("com.example")
@EnableJpaRepositories("com.example")

public class DemoApplication {

	   private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) { 
		SpringApplication.run(DemoApplication.class, args);
		logger.info("Demo application is running");
	}

}
