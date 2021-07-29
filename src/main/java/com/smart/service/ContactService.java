package com.smart.service;

import java.util.List;
import java.util.Optional;

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
	
	
	public Contact getContact(int id) {
		
		Optional<Contact> findById = contactRepo.findById(id);
		Contact contact = findById.get();
		return contact;
		
	}

	public void deleteContact(int id) {
	
		contactRepo.deleteById(id);
		
	}


	public Contact updateCon(Contact contact) {
		Contact save = contactRepo.save(contact);
		return save;
	}


	public List<Contact> getcontactsOfUser(User user) {
		
		List<Contact> findAllByUser = contactRepo.findAllByUser(user);
		return findAllByUser;
	}
	
}


