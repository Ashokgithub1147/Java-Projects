package com.ashok.spring.securiry.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springsecuroty/v1/management")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	@GetMapping("/get")
	public String getAdmin() {
		return "Secured End Point - ADMIN";
	}
}
