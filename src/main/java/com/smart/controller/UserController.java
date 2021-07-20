package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	// method for adding common data to the view.
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		
		String name = principal.getName();
		User user = userService.getUser(name);
		model.addAttribute("title", "Hello, " +user.getUserName() + " | smart contact manager");
		model.addAttribute("user", user);
	}
	
	// method for the user dashboard 
	
	@GetMapping("/index")
	public String userDashboard(Model model, Principal principal) {

		return "normal/user_dashboard";
	}
	
	//method for adding contacts handler
	
	@GetMapping("/add-contact")
	public String addContactForm(Model model) {
		model.addAttribute("contact", new Contact());
		model.addAttribute("title", " add-contact | smart contact manager");
		return "normal/add_contact_form";
	}
	
	@PostMapping("/submit_contact")
	public String saveContact(@ModelAttribute Contact contact, Principal principal) {
		
		String name = principal.getName();
		User user = userService.getUser(name);
		
		contact.setUser(user);
		
		user.getContacts().add(contact);
		
		userService.setUser(user);
		
		System.out.println(contact);
		
		return "normal/add_contact_form";
	}
	
	
}
