
package com.example.springbootrestserver.component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
@Component
public class CrossDomainFilter extends OncePerRequestFilter {
	
	   private static final Logger logger = LoggerFactory.getLogger(CrossDomainFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*"); //toutes les URI sont autorisées
           httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
           httpServletResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-req");           
           filterChain.doFilter(httpServletRequest, httpServletResponse);  
           logger.info("all the URI are authorised");
           
    }
}