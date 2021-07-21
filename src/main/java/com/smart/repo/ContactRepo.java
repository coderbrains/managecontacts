package com.smart.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entity.Contact;
import com.smart.entity.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {

	List<Contact> findAllByUser(User user);

	
	
}
