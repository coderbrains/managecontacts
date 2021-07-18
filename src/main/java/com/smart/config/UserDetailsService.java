package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.entity.User;
import com.smart.service.UserService;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = service.getUser(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("user not found !!");
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}
}
