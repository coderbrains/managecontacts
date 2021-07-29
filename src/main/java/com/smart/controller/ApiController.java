package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
		return "getcontactsOfUser";
	}
}
