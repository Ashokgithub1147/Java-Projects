package com.ashok.spring.securiry.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springsecurity/v1/user")
public class UserController {

	@GetMapping("/get")
	public String getHello() {
		return "Hello User Controller method() called";
	}
}
