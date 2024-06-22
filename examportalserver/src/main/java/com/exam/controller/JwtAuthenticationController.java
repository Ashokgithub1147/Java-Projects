package com.exam.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.services.JwtUserDetailsService;
import com.exam.services.UserService;
import com.exam.utilities.JwtRequest;
import com.exam.utilities.JwtTokenUtility;
import com.exam.utilities.ResponseBean;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class JwtAuthenticationController {
	@Autowired private JwtTokenUtility jwtTokenUtility;
	@Autowired private JwtUserDetailsService jwtUserDetailsService;
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private UserRepository userRepository;
	@Autowired private UserService userService;
//	@Autowired private Logger logger;
//	class LoginData{
//		public String token;
//		public User user;
//	}
	class JWTToken{
		public String token;
	}
	@GetMapping(value="/get")
	public String getData() {
		return "Data came";
	}
	
	@PostMapping("/generate-token")
	public ResponseBean generateJwtToken(ServletRequest req, ServletResponse res, @RequestBody JwtRequest jwtRequest) {
		ResponseBean response = new ResponseBean();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
		}catch(BadCredentialsException e){
			System.out.println("Exception occured :"+e.getMessage());
			response.setMessage(e.getMessage());
			response.setStatus(HttpStatus.UNAUTHORIZED);
			return response;
		}
//		try {
			
			final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUserName());
			final String token = jwtTokenUtility.generateToken(userDetails);
//		Optional<User> userOptional = userRepository.findByuserName(jwtRequest.getUserName());
//		User userData = null;
//		if(userOptional.isPresent()) {
//			userData = userOptional.get();
//		}
//		LoginData loginData = new LoginData();
//		loginData.token = token;
//		loginData.user = userData;
			response.setStatus(HttpStatus.OK);
			response.setMessage("JWT Token generated successfully!");
			JWTToken jwtToken = new JWTToken();
			jwtToken.token = token;
			response.setData(jwtToken);
//		} catch(UsernameNotFoundException ex) {
//			response.setStatus(HttpStatus.NOT_FOUND);
//			response.setMessage(ex.getMessage());
//			logger.info(ex.getMessage());
//		}
		return response;
	}
	/**
	 * This API will return details of current logged in user
	 * @param principal
	 * @return ResponesBean
	 * @throws Exception
	 */
	@GetMapping("/current-user")
	public ResponseBean getCurrentUser(Principal principal) {
		ResponseBean response = new ResponseBean();
		try {
			response= this.userService.getUser(principal.getName());
		} catch(Exception e) {
			response.setStatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage(e.getMessage());
		}
		return response;
		
	}
}
