package com.exam.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.services.UserService;
import com.exam.utilities.ResponseBean;

/**
 * user controller which contains user related REST API's
 * @author Ashok Ulava
 */
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired UserService userService;
	private static final Long normalUserRole = 32L;
	private static final Long adminUserRole = 44L;
	/**
	 * create user
	 * @param user
	 * @param userRoles
	 */
	@PostMapping(value="/createUser")
	public ResponseBean createUser(@RequestBody User user) {
		ResponseBean response = new ResponseBean();
		try {
			Set<UserRole> roles = new HashSet<>();
			
			Role role = new Role();
			role.setId(normalUserRole);
			role.setRoleName("NORMAL");
			
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			
			//adding userRole to roles set
			roles.add(userRole);
			response = userService.createUser(user, roles);
		}catch(Exception e) {
			System.out.println("Exception occured "+e.getMessage());
			response.setMessage("Create User API failed with Exception : "+e.getMessage());
		}
		
		return response;
	}
	/**
	 * getUser
	 * 
	 * @param userName
	 * @return response
	 */
	@GetMapping(value="/getUser")
	public ResponseBean getUser(@RequestParam("userName") String userName) {
		ResponseBean response = new ResponseBean();
		try {
			response = userService.getUser(userName);
		}catch(Exception e) {
			response.setMessage("Get User API failed with exception : "+e.getMessage());
		}
		return response;
	}
	
	@DeleteMapping(value="/deleteUserById")
	public ResponseBean deleteUser(@RequestParam("userId") Long userId) {
		ResponseBean response = new ResponseBean();
		try {
			response = this.userService.deleteUser(userId);
		}catch(Exception e) {
			response.setMessage("Delete User API failed with exception : "+e.getMessage());
		}
		return response;
	}
	
}
