package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.repo.ContactRepo;

@Service
public class ContactService {

	@Autowired
	private ContactRepo contactRepo;
	
	public Page<Contact> getContacts(User user, Pageable pageable){
		
		Page<Contact> findAllByUser = contactRepo.findAllByUser(user, pageable);
		
		return findAllByUser;
	}
	
}


