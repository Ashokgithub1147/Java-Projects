package com.ashok.spring.securiry.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
}
