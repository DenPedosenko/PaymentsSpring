package com.epam.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getUsers(Model model, @RequestParam(required = false, defaultValue = "true") String ascending,
			@RequestParam(defaultValue = "lastName", required = false) String orderBy,
			@RequestParam(defaultValue = "1", required = false) Integer page){
		Pageable pageable = PageRequest.of(page - 1, 10, ascending.equals("true")?Sort.by(orderBy):Sort.by(orderBy).descending());
		Page<User> users = userService.findAllUsers(pageable);
		model.addAttribute("users", users.toList());
		model.addAttribute("page", page);
		model.addAttribute("size", users.getTotalPages());
		model.addAttribute("locale", LocaleContextHolder.getLocale());
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("ascending", ascending);
		return "users";
	}
	
	@GetMapping("users/blockUser")
	public String blockUser(@RequestParam Integer id) {
		userService.blockUser(id);
		return "redirect:/users";
	}
	@GetMapping("users/unlockUser")
	public String unlockUser(@RequestParam Integer id) {
		userService.unlockUser(id);
		return "redirect:/users";
	}
	

}
