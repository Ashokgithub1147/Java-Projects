package com.ashok.spring.securiry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ashok.spring.securiry.model.AuthenticationRequest;
import com.ashok.spring.securiry.model.RegisterRequest;
import com.ashok.spring.securiry.model.User;
import com.ashok.spring.securiry.repository.UserRepository;
import com.ashok.spring.securiry.util.ResponseBody;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

	@Autowired private UserRepository userRepository;
	@Autowired private JwtService jwtService;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private AuthenticationManager authenticationManager;
	/**
	 * This method is used to register new user in system
	 * @param registerRequest
	 * @return {@link ResponseBody}
	 */
	public ResponseBody register(RegisterRequest registerRequest){
		User user = new User();
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setUnencryptedPassword(registerRequest.getPassword());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setRole(registerRequest.getRole());
		
		User savedUser = this.userRepository.save(user);
		
		String jwtToken = jwtService.generateToken(user);
		ResponseBody responseBody = new ResponseBody();
		responseBody.setJwtToken(jwtToken);
		return responseBody;
	}
	/**
	 * This method is used to authenticate user with username and password
	 * @param authenticationRequest
	 * @return ResponseBody
	 */
	public ResponseBody authenticateUser(AuthenticationRequest authenticationRequest) {
		//validate username and password
		//Use any AuthenticationProvider
		//Authenticate using authenticationManager
		ResponseBody response = new ResponseBody();
		try {			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
					);
			//verify user present  in database
			User user = userRepository.findByEmail(authenticationRequest.getUserName()).orElseThrow();
			
			//generate JWT token
			String jwtToken = jwtService.generateToken(user);
			response.setJwtToken(jwtToken);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return response;
	}
}
