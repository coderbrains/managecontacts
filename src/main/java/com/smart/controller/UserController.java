package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.entity.User;
import com.smart.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public String userDashboard(Model model, Principal principal) {
		String name = principal.getName();
		User user = userService.getUser(name);
		
		model.addAttribute("user", user);
		model.addAttribute("title", "Hello, " +user.getUserName() + " | smart contact manager");
		
		return "normal/user_dashboard";
	}
	
}
