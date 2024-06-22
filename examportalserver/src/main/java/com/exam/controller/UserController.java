package com.exam.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("examportal/user")
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
			response = userService.createUser(user);
		}catch(Exception ex) {
			System.out.println("Exception occured "+ex.getMessage());
			response.setMessage("Create User API failed with Exception : "+ex.getMessage());
		}
		
		return response;
	}
	/**
	 * getUser
	 * 
	 * @param userName
	 * @return ResponseBean
	 */
	@GetMapping(value="/getUser")
	public ResponseBean getUser(@RequestParam("userName") String userName) {
		ResponseBean response = new ResponseBean();
		try {
			response = userService.getUser(userName);
		}catch(Exception e) {
			response.setStatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("Get User API failed with exception : "+e.getMessage());
		}
		return response;
	}
	@PutMapping(value="/updateUser")
	public ResponseBean updateUser(@RequestBody User user) {
		ResponseBean response = new ResponseBean();
		try {
			response = this.userService.updateUser(user);
		}catch(Exception ex) {
			response.setStatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("Update User API failed with exception : "+ex.getMessage());
		}
		return response;
	}
	/**
	 * delete User
	 * @param userId
	 * @return ResponseBean
	 */
	@DeleteMapping(value="/deleteUserById")
	public ResponseBean deleteUser(@RequestParam("userId") Long userId) {
		ResponseBean response = new ResponseBean();
		try {
			response = this.userService.deleteUser(userId);
		}catch(Exception ex) {
			response.setStatus(HttpStatus.EXPECTATION_FAILED);
			response.setMessage("Delete User API failed with exception : "+ex.getMessage());
		}
		return response;
	}
	
}
