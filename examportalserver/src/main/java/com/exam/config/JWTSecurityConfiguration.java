package com.exam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class JWTSecurityConfiguration{
	@Autowired private JwtAuthRequestFilter jwtRequestFilter;
	@Autowired private AuthenticationProvider authenticationProvider;
	@Autowired private CustomJwtAuthenticatioinEntryPoint jwtAuthenticatioinEntryPoint;
	@Autowired private CustomAccessDeniedHandler accessDeniedHandler;
	/**
	 * This method will allow requests to be validated and make session stateless and 
	 * adds filter to validate the token with every request and also disable authorization to specified requests
	 * and throws exception if request is unauthorized
	 */
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req -> req
                .requestMatchers("examportal/user/createUser", "examportal/user/generate-token").permitAll()
                .anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .exceptionHandling(exceptionHandler->
	        exceptionHandler.authenticationEntryPoint(jwtAuthenticatioinEntryPoint)
	        .accessDeniedHandler(accessDeniedHandler))
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}

