package com.ashok.spring.securiry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashok.spring.securiry.model.AuthenticationRequest;
import com.ashok.spring.securiry.model.RegisterRequest;
import com.ashok.spring.securiry.service.AuthService;
import com.ashok.spring.securiry.util.ResponseBody;

@RestController
@RequestMapping("/springsecurity/auth/v1")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseBody> register(@RequestBody RegisterRequest registerRequest){
		ResponseBody response = new ResponseBody();
		try {
			response = authService.register(registerRequest);
			
		}catch(Exception ex) {
			
		}
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<ResponseBody> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
		ResponseBody response = new ResponseBody();
		try {
			response = this.authService.authenticateUser(authenticationRequest);
		}catch(Exception e) {
			
		}
		return ResponseEntity.ok(response);
	}
	
}
