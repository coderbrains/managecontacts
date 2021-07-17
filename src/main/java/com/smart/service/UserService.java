package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.User;
import com.smart.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	
	public User setUser(User user) {
		
		User save = userRepo.save(user);
		return save;
		
	}
	
}
