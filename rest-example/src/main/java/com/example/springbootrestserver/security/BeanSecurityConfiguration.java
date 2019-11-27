package com.example.springbootrestserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// websecurity configuration
  
@Configuration
@EnableWebSecurity
public  class BeanSecurityConfiguration extends  WebSecurityConfigurerAdapter   {

   @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

   @Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user")
            .password("password")
            .roles("USER")
            .and()
          .withUser("admin")
            .password("admin")
            .roles("USER", "ADMIN");
    }
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/users")
            .hasAnyRole("USER", "ADMIN").antMatchers("/users").hasAnyRole("USER", "ADMIN")
            .antMatchers("/user").hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
            .permitAll().and().logout().permitAll();
        http.httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/user/*")
        .hasAnyRole("USER", "ADMIN")
        .antMatchers("/users/*")
        .hasAnyRole("USER", "ADMIN")
        .and()
        .formLogin().permitAll();

        http.csrf().disable();
    }

 
}