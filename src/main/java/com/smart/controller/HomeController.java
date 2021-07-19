package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entity.User;
import com.smart.helper.Message;
import com.smart.service.UserService;

@Controller
public class HomeController {

	@Autowired
    private	UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "Home | smartcontact manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "about-smartcontactmanager");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("title", "register-smartcontactmanager");
		return "signup";
	}

	@PostMapping("/do_signup")
	public String signuppage(@Valid @ModelAttribute("user") User user , BindingResult result,
			@RequestParam(value = "check", defaultValue = "false") boolean check, Model model, HttpSession session) {

		try {

			if (!check) {
				throw new Exception("You have not checked the terms and conditions");
			} else if (result.hasErrors()) {
				System.out.print(result);
				model.addAttribute("user", user);
				return "signup";
			} else {

				model.addAttribute("user", user);

				// setting the user default values.
				user.setEnable(true);
				user.setUserRole("ROLE_USER");
				user.setImageUrl("default.png");
				
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				
				userService.setUser(user);
				session.setAttribute("message", new Message("Successfully registered.", "alert-success"));
				return "signup";
			}
		} catch (Exception e) {

			model.addAttribute("user", user);
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong.", "alert-danger"));
			return "signup";
		}

	}

}
