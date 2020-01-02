package com.example.springbootrestserver.swagger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

    @Configuration
	@EnableSwagger2
	@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
	public class SwaggerConfiguration {

	
	Contact contact = new Contact(
        "Safa Krifi",
        "https://github.com/safakrifi",
        "safa.krifi@altersis.com");

    List<VendorExtension> vext = new ArrayList<>();
	 ApiInfo apiInfo = new ApiInfo(
	            "Backend API",
	            "This is the best stuff since sliced bread - API",
	            "5.0.0",
	            "https://github.com/safakrifi/",
	            contact,
	            "EX",
	            "https://github.com/safakrifi/",
	            vext);
	@Bean
	public Docket newsApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            .apis(RequestHandlerSelectors.any())
	            .paths(PathSelectors.any())
	            .build()
	            .securitySchemes(Lists.newArrayList(apiKey()))
	            .securityContexts(Lists.newArrayList(securityContext()))
	            .apiInfo(apiInfo);
	}

	@Bean
	SecurityContext securityContext() {
	    return SecurityContext.builder()
	            .securityReferences(defaultAuth())
	            .forPaths(PathSelectors.any())
	            .build();
	}

	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	            = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Lists.newArrayList(
	            new SecurityReference("JWT", authorizationScopes));
	}

	private ApiKey apiKey() {
		
	    return new ApiKey("JWT", "Authorization", "header");
	} 
	

	
	}

