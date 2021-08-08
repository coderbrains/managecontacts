package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.service.ContactService;
import com.smart.service.UserService;

@RestController
public class ApiController {

	@Autowired
	ContactService contactService;
	@Autowired
	UserService userService;
	
	@GetMapping("/getusercontacts")
	public String getContacts()
	{
		return "getcontactsOfUser testing of api...";
	}
	
	@PostMapping("/getcontacts")
	public ResponseEntity<Object> getcontacts(@RequestBody int id)
	{
		try {
		

			User user = userService.getUserusingField(id);
			List<Contact> contacts = user.getContacts();
			if(contacts.size()==0)
			{
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("no contacts found.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(contacts);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not authorized.");
		}
	}
	
	@GetMapping("/searchcontacts/{query}")
	public ResponseEntity<?> searchcontacts(@PathVariable("query") String query, Principal principal)
	{
		System.out.println(query);
		String name = principal.getName();
		User user = userService.getUser(name);
		List<Contact> search = contactService.search(query,user);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(search);
	}
}
