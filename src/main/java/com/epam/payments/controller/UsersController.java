package com.epam.payments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epam.payments.model.User;
import com.epam.payments.service.UserService;

@Controller
public class UsersController {

	private final UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/users")
	public String getUsers(Model model){
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("user", userService.getById(3));
		model.addAttribute("locale", LocaleContextHolder.getLocale());
		return "users";
	}
	

}
