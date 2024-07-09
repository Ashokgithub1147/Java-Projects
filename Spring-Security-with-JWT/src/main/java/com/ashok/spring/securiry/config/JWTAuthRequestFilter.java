package com.ashok.spring.securiry.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ashok.spring.securiry.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Custom JWT Filter that will intercept each httpRequest and 
 * validates the request with JWT token provided along
 * with the filter
 * @author aulava (Ashok Ulava)
 */
@Component
public class JWTAuthRequestFilter extends OncePerRequestFilter {

	@Autowired private JwtService jwtService;
	@Autowired private UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		//collect jwtTokenHeader
		final String authTokenHeader = request.getHeader("Authorization");
		if(authTokenHeader==null && !authTokenHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
		}
		//collect jwt token from header
		String jwtToken = authTokenHeader.substring(7);
		//collect username from token
		String userName = this.jwtService.getUserNameFromToken(jwtToken);
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			//if user is valid and security context is null then set
			//the user in SecurityContextHolder
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().contains("/springsecurity/auth");
	}

	
}
