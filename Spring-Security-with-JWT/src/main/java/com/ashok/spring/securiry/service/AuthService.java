package com.ashok.spring.securiry.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ashok.spring.securiry.model.RegisterRequest;
import com.ashok.spring.securiry.util.ResponseBody;

@Service
public class AuthService {

	public ResponseEntity<ResponseBody> register(RegisterRequest registerRequest){
		return null;
	}
}
