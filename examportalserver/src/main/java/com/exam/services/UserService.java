package com.exam.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repository.RoleRepository;
import com.exam.repository.UserRepository;
import com.exam.utilities.ExamPortalAPIMessages;
import com.exam.utilities.ResponseBean;

import jakarta.transaction.Transactional;

/**
 * This is UserService class where we write business logic of API's related to Users in examportalserver application
 * @author Ashok Ulava
 */
@Service
@Transactional
public class UserService {

	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	/** create user
	 * 
	 * @param user
	 * @param userRoles
	 */
	public ResponseBean createUser(User user) throws Exception{
		ResponseBean response = new ResponseBean();
		
		//checking does user present in database or not
		Optional<User> optionalUser = userRepository.findByuserName(user.getUserName());
		User localUser = null;
		if(optionalUser.isPresent()) {
			localUser = optionalUser.get();
		}
		if(localUser!=null) {
			System.out.println("User Already Present");
			response.setStatus(HttpStatus.OK);
			response.setMessage(ExamPortalAPIMessages.user_already_present);
		} else {
			Set<UserRole> userRoles = new HashSet<>();

			//get roles from database and map them to user roles
			Set<Long> roleIds = user.getRoleIds();
			for(Long roleId: roleIds) {
				Optional<Role> roleOptional = this.roleRepository.findById(roleId);
				if(roleOptional.isPresent()) {					
					Role role = roleOptional.get();
					UserRole userRole = new UserRole();
					userRole.setRole(role);
					userRole.setUser(user);
					
					userRoles.add(userRole);
				}
			}

			
			//create user
			//saving unencrypted password
			user.setUnencryptedPassword(user.getPassword());
			//encode password
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			//adding all userRoles to user
			user.getUserRoles().addAll(userRoles);
			User savedUser = this.userRepository.save(user);
			if(savedUser!=null) {
				response.setStatus(HttpStatus.OK);
				response.setData(savedUser);
				response.setMessage(ExamPortalAPIMessages.user_creation_success);
			}else {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessage(ExamPortalAPIMessages.user_creation_failed);
			}
		}
		return response;
	}

	public ResponseBean getUser(String userName) throws Exception{
		ResponseBean response = new ResponseBean();
		Optional<User> user = this.userRepository.findByuserName(userName);
		if(user.isPresent()) {
			response.setStatus(HttpStatus.OK);
			response.setData(user);
			response.setMessage("User data received successfully ");
		} else {
			response.setStatus(HttpStatus.OK);
			response.setMessage("User not present with given username: "+userName);
		}
		return response;
	}
	
//	public ResponseBean updateUser()

	public ResponseBean deleteUser(Long userId) throws Exception{
		ResponseBean response = new ResponseBean();
		Optional<User> userOptional = this.userRepository.findById(userId);
//		User user=null;
		if(userOptional.isPresent()) {
//			user = userOptional.get();
//			this.userRepository.delete(user);
			this.userRepository.deleteById(userId);
			response.setStatus(HttpStatus.OK);
			response.setMessage("User deleted successfully");
		} else {
			response.setStatus(HttpStatus.OK);
			response.setMessage("User Not found with given  id:"+userId);
		}
		return response;
	}
	
}
