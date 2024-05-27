package com.ashok.spring.securiry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashok.spring.securiry.model.RegisterRequest;

@RestController("springsecurity")
public class AuthController {

	@PostMapping("auth/v1/register")
	public ResponseEntity<RegisterRequest> register(@RequestBody RegisterRequest registerRequest){
		return null;
	}
}
