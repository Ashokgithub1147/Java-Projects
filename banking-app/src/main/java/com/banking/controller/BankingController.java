package com.banking.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController(value = "/banking")
public class BankingController {

	@GetMapping("/get")
	public ResponseEntity<String> getMethodName(@RequestParam String param) {
		return new ResponseEntity<String>("String returned", HttpStatus.OK);
	}
	
}
