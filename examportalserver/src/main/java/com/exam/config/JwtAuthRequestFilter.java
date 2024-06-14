package com.exam.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.utilities.JwtTokenUtility;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthRequestFilter extends OncePerRequestFilter {

	@Autowired private JwtTokenUtility jwtTokenUtility;
	@Autowired private UserDetailsService userDetailsService;
//	@Autowired private Logger logger;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String userName = null;
		String token = null;
		
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
			token = requestTokenHeader.substring(7);
			try {
				userName = jwtTokenUtility.getUsernameFromToken(token);
			}catch(IllegalArgumentException e) {
				System.out.println("Unable to ger JWT token");
			} catch(ExpiredJwtException e) {
				System.out.println("JWT token was expired");
			}
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			try {
				
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
				if(jwtTokenUtility.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new 
							UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					//wrongly implemented even though it worked ...???
					usernamePasswordAuthenticationToken.setDetails(userDetails);
//				usernamePasswordAuthenticationToken.setDetails(
//						new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch(UsernameNotFoundException ex) {
				logger.info(ex.getMessage());
//				System.out.println(ex.getMessage());
			}
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().contains("/user/createUser") 
				|| request.getServletPath().contains("/user/generate-token");
	}

}
