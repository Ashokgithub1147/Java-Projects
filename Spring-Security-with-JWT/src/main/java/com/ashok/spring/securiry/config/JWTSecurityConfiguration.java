package com.ashok.spring.securiry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ashok.spring.securiry.model.Role;

@Configuration
@EnableWebSecurity
public class JWTSecurityConfiguration {

	@Autowired private AuthenticationProvider authenticationProvider;
	@Autowired private JWTAuthRequestFilter jwtAuthRequestFilter;
	@Autowired private JWTAuthEntryPoint jwtAuthEntryPoint;
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(AbstractHttpConfigurer::disable)
       		.authorizeHttpRequests(req->
       				req.requestMatchers("/springsecurity/auth/**")
       				.permitAll()
       				.requestMatchers("/springsecurity/v1/management/**").hasAnyRole(Role.ADMIN.name(),Role.MEMBER.name())
       				.anyRequest()
       				.authenticated())
       		//adding security headers
       		.headers(headers->
       				headers.frameOptions(
       						frameOption->frameOption.sameOrigin()))
       		//making session stateless
       		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       		//adding authentication provider
       		.authenticationProvider(authenticationProvider)
       		//adding exception handling
       		.exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthEntryPoint))
       		//adding jwtAuthRequestFilter
       		.addFilterBefore(jwtAuthRequestFilter, UsernamePasswordAuthenticationFilter.class);
       
       return http.build();
    }
}
