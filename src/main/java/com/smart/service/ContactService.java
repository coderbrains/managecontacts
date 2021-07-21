package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.repo.ContactRepo;

@Service
public class ContactService {

	@Autowired
	private ContactRepo contactRepo;
	
	public List<Contact> getContacts(User user){
		
		List<Contact> findAllByUser = contactRepo.findAllByUser(user);
		
		return findAllByUser;
	}
	
}


