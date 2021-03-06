package com.smart.repo;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entity.Contact;
import com.smart.entity.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {

	Page<Contact> findAllByUser(User user, Pageable pageable);

	List<Contact> findAllByUser(User user);

	List<Contact> findByNameContainingAndUser(String query, User user);

	
	
}
