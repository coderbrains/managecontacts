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
	
	
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}
	
	public User getUserusingField(int id) {
		
		User byId = userRepo.getById(id);
		return byId;
	}


	public User updateUser(User user) {
		
		User save = userRepo.save(user);
		return save;
	}


	public void deleteUserById(int userId) {
		
		userRepo.deleteById(userId);
	}
}
