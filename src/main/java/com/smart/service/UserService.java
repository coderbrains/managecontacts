package com.smart.service;

import java.util.Optional;

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
		
		Optional<User> findById = userRepo.findById(id);
		User user = findById.get();
		return user;
		
	}


	public User updateUser(User user) {
		
		User save = userRepo.save(user);
		return save;
	}


	public void deleteUserById(int userId) {
		
		userRepo.deleteById(userId);
	}
}
