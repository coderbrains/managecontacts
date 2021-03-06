package com.smart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
				contact.setImageUrl("contact.png");

			} else {
				// the file is not empty...

				String originalFilename = System.currentTimeMillis() + "_" + image.getOriginalFilename();

				contact.setImageUrl(originalFilename);

				File file = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(file.getAbsolutePath() + File.separator + originalFilename);

				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("file is uploaded...");

				session.setAttribute("message", new Message("contact is added successfully..", "success"));
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

	// this is the handler for the view contacts in the user sidebar menu.
	@GetMapping("/viewcontacts/{page}")
	public String viewContacts(@PathVariable("page") int page, Model model, Principal principal) {
		model.addAttribute("title", "view-contacts | smartcontact-manager");

		String name = principal.getName();
		User user = userService.getUser(name);

		Pageable of = PageRequest.of(page, 5);

		Page<Contact> contacts = contactService.getContacts(user, of);

		model.addAttribute("contacts", contacts);
		model.addAttribute("totalPages", contacts.getTotalPages());
		model.addAttribute("currentPage", page);

		return "normal/viewcontacts";
	}

	// controller for viewing details....
	@GetMapping("/viewcontact/{id}")
	public String viewDetails(@PathVariable("id") int id, Model model, Principal principal) {

		model.addAttribute("title", "viewcontact | smart contact manager");

		try {
			String name = principal.getName();

			User user = userService.getUser(name);

			Contact contact = contactService.getContact(id);

			if (contact.getUser().equals(user)) {
				model.addAttribute("contact", contact);
				return "normal/view_contact_details.html";

			} else {

				return "unauthorized";

			}

		} catch (Exception e) {
			return "unauthorized";
		}

	}

	@GetMapping("/deletecontact/{id}")
	public String deleteContact(@PathVariable("id") int id, Principal principal, HttpSession httpSession) {

		String name = principal.getName();

		User user = userService.getUser(name);

		Contact contact = contactService.getContact(id);

		if (contact.getUser().getUserId() != user.getUserId()) {
			httpSession.setAttribute("cannot delete contact", "success");
			return "redirect:/user/viewcontacts/0";

		} else {

			contactService.deleteContact(id);
		}
		httpSession.setAttribute("message", new Message("deleted successfully", "danger"));

		return "redirect:/user/viewcontacts/0";
	}

	// UPDATION OF THE CONTACT CLICKED ACCORDINGLY...

	@GetMapping("/updateContact/{id}")
	public String updateContact(@PathVariable("id") int id, Model model, Principal principal) {

		try {

			String name = principal.getName();
			User user = userService.getUser(name);

			model.addAttribute("title", "update contact | smart contact manager.");
			Contact contact = contactService.getContact(id);
			model.addAttribute("contact", contact);

			if (user.getUserId() != contact.getUser().getUserId()) {
				return "unauthorized";
			}
			return "normal/updateContact";

		} catch (Exception e) {
			e.printStackTrace();
			return "unauthorized";
		}

	}

	// submission of the update contact
	@PostMapping("/updateContact_submit/{id}")
	public String update(@ModelAttribute Contact contact1, @RequestParam("image") MultipartFile image,
			@PathVariable("id") int id, Principal principal) {

		try {

			Contact contact = contactService.getContact(id);

			String name = principal.getName();
			User user = userService.getUser(name);

			if (user.getUserId() != contact.getUser().getUserId()) {
				return "unauthorized";
			} else {

				contact.setName(contact1.getName());
				contact.setEmail(contact1.getEmail());

				if (!image.isEmpty()) {

					String originalFilename = System.currentTimeMillis() + "_" + image.getOriginalFilename();

					contact.setImageUrl(originalFilename);

					File file = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(file.getAbsolutePath() + File.separator + originalFilename);

					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				}

				contact.setMobileNumber(contact1.getMobileNumber());

				contact.setContactDescription(contact1.getContactDescription());

				contact.setWork(contact1.getWork());
				contact.setNickName(contact1.getNickName());

				Contact updateCon = contactService.updateCon(contact);

				System.out.println(updateCon);

				return "redirect:/user/viewcontacts/0";

			}

		} catch (Exception e) {
			e.printStackTrace();
			return "unauthorized";
		}

	}

	// profile section...
	@GetMapping("/profile")
	public String profile(Model model, Principal principal) {

		String name = principal.getName();
		User user = userService.getUser(name);

		model.addAttribute("user", user);

		model.addAttribute("title", "Profile page | Smartcontact manager");
		return "normal/profile";
	}

	// update user
	@GetMapping("/updateUser")
	public String updateUser(Principal principal, Model model) {

		model.addAttribute("title", "Update user| smart contact manager");

		String name = principal.getName();
		User user = userService.getUser(name);

		model.addAttribute("user", user);

		return "normal/updateuser";
	}

	// submit updated user
	@PostMapping("/submitUser")
	public String submitUser(@ModelAttribute User user, @RequestParam("imageUser") MultipartFile image,
			Principal principal) throws IOException {

		if (image.isEmpty()) {
			System.out.println("file has no content..");

		} else {

			System.out.println("file has some content..");

			String originalFilename = System.currentTimeMillis() + "_" + image.getOriginalFilename();

			user.setImageUrl(originalFilename);

			File file = new ClassPathResource("static/img").getFile();

			Path path = Paths.get(file.getAbsolutePath() + File.separator + originalFilename);

			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}

		String name = principal.getName();
		User user2 = userService.getUser(name);

		user2.setEmail(user.getEmail());
		user2.setImageUrl(user.getImageUrl());
		user2.setUserAbout(user.getUserAbout());
		user2.setUserName(user.getUserName());

		User updateUser = userService.updateUser(user2);

		System.out.println(updateUser);

		return "redirect:/user/profile";

	}

	// deleting the user at single click..

	@GetMapping("/deleteUser")
	public String deleteUser(Principal principal, HttpSession session) {

		String name = principal.getName();
		User user = userService.getUser(name);
		userService.deleteUserById(user.getUserId());

		session.setAttribute("message",
				new Message("your account has been deleted successfully ! this cannot be undone!!!", "danger"));
		return "redirect:/signin";

	}

}
