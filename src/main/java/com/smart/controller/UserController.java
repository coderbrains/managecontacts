package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Message;
import com.smart.service.ContactService;
import com.smart.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ContactService contactService;

	// method for adding common data to the view.

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {

		String name = principal.getName();
		User user = userService.getUser(name);
		model.addAttribute("title", "Hello, " + user.getUserName() + " | smart contact manager");
		model.addAttribute("user", user);
	}

	// method for the user dashboard

	@GetMapping("/index")
	public String userDashboard(Model model, Principal principal) {

		return "normal/user_dashboard";
	}

	// method for adding contacts handler

	@GetMapping("/add-contact")
	public String addContactForm(Model model) {
		model.addAttribute("contact", new Contact());
		model.addAttribute("title", " add-contact | smart contact manager");
		return "normal/add_contact_form";
	}

	@PostMapping("/submit_contact")
	public String saveContact(@ModelAttribute Contact contact, @RequestParam("image") MultipartFile image,
			Principal principal, HttpSession session) {

		try {

			if (image.isEmpty()) {
				// if the file is empty then use this
				System.out.println("file is empty..");

			} else {
				// the file is not empty...

				String originalFilename = System.currentTimeMillis() + "_" + image.getOriginalFilename();

				contact.setImageUrl(originalFilename);

				File file = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(file.getAbsolutePath() + File.separator + originalFilename);

				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("file is uploaded...");
				
				
				session.setAttribute("message", new Message("File is added successfully..", "success"));
			}

			String name = principal.getName();
			User user = userService.getUser(name);

			contact.setUser(user);

			user.getContacts().add(contact);

			userService.setUser(user);

			System.out.println(contact);

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
			e.printStackTrace();

			session.setAttribute("message", new Message("something went wrong!! try again.", "danger"));
		}

		return "normal/add_contact_form";
	}
	
	
	//this is the handler for the view contacts in the user sidebar menu.
	@GetMapping("/viewcontacts")
	public String viewContacts(Model model, Principal principal) {
		model.addAttribute("title", "view-contacts | smartcontact-manager");
		
		String name = principal.getName();
		User user = userService.getUser(name);
		
		List<Contact> contacts = contactService.getContacts(user);
		
		model.addAttribute("contacts", contacts);
		
		return "normal/viewcontacts";
	}
	
	
	

}
