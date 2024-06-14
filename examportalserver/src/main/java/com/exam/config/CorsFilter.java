//package com.exam.config;
//
//import java.io.IOException;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
///**
// * This class is used for filtering the web requests
// * @author aulava
// *
// */
//@WebFilter
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class CorsFilter implements Filter {
//
//	/**
//	 * This method filters every request and response going through applications
//	 * 
//	 * @param ServletRequest  req
//	 * @param ServletResponse res
//	 * @param FilterChain     chain
//	 */
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) res;
//
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,OPTIONS,DELETE,HEAD");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Headers",
//				"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,X-Custom-Header,Access-Control-Allow-Headers");
//		response.setHeader("Set-Cookie", "key=value; HttpOnly; SameSite=strict");
//
//		if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
//			response.setStatus(HttpStatus.NO_CONTENT.value());
//		} else {
//			chain.doFilter(request, response);
//		}
//	}
//
//}
