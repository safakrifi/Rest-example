package com.example.springbootrestserver.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springbootrestserver.jwt.filters.JwtRequestFilter;
import com.example.springbootrestserver.service.UserService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

// JWT websecurity configuration

@Configuration
@EnableWebSecurity
@EnableSwagger2
public class BeanSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
		
	}

	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	        return bCryptPasswordEncoder;
	    }
	 
	   @Override
	   public void configure(WebSecurity web) throws Exception {
	     web.ignoring()
	       .antMatchers(
	         "/v2/api-docs",
	         "/swagger-resources/**",
	         "/swagger-ui.html**",
	         "/webjars/**");
	   }
	 
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 
		http.authorizeRequests().antMatchers("/console/**").permitAll().antMatchers("/swagger-ui.html/**").permitAll()
		.and().csrf().disable().authorizeRequests().antMatchers("/user/authenticate").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	
	
    http.headers().frameOptions().disable();

	}
	

	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean ()throws Exception{
    	return super.authenticationManagerBean();
    }
	

	// Enable jdbc authentication
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
	}

}