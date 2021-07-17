package com.smart.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User	, Integer>{

	
	
}
