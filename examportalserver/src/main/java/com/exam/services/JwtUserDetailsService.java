package com.exam.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.exam.model.User;
import com.exam.repository.UserRepository;

@Component
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByuserName(username);
		if(!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User not found with usrename: "+username);
		}
		User user = userOptional.get();
		System.out.println("User "+user);
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),new ArrayList<>());
	}
	
	
}
