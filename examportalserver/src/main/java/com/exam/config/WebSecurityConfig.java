package com.exam.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.exam.services.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired private JwtUserDetailsService jwtUserDetailsService;
	@Autowired private JwtAuthenticatioinEntryPoint jwtAuthenticatioinEntryPoint;
	@Autowired private JwtRequestFilter jwtFilter;

	//overridden this method at first to test spring security working or not
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(jwtUserDetailsService);
//	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	/**
	 * This method will allow requests to be validated and make session stateless and 
	 * adds filter to validate the token with every request and also disable authorization to specified requests
	 * and throws exception if request is unauthorized
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			//we don't need CSRF
			.disable()
			.authorizeRequests()
			//don't authenticate particular requests specified here inside antMatchers() method
			.antMatchers("/user/generate-token")
			.permitAll()
			//any request which is not specified in above antMatchers should be authenticated
			.anyRequest()
			.authenticated()
			.and()
			//throws exception if request is unauthorized
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticatioinEntryPoint)
			.and()
			//make sure we use stateless session, session won't be used to store Users data
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//Adding filter to validate the token with every request
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
//		http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
//		.addFilterBefore(jwtFilter, CorsFilter.class);
	}
}

