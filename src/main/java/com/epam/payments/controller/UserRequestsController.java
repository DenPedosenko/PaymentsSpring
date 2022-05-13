package com.epam.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.service.UserRequestService;
import com.epam.payments.service.UserService;

@Controller
public class UserRequestsController {
	private UserRequestService requestService;
	private UserService userService;
	
	
	@Autowired
	public UserRequestsController(UserRequestService requestService, UserService userService) {
		this.requestService = requestService;
		this.userService = userService;
	}
	
	@GetMapping("/dismissRequest")
	private String dismissUserUnlockRequest(@RequestParam int id) {
		requestService.changeRequestStatusToClosed(id);
		return "redirect:/";	
	}
	
	@GetMapping("/acceptRequest")
	private String acceptUserUnlockRequest(@RequestParam int id) {
		requestService.acceptUserRequest(id);
		return "redirect:/";	
	}
	
	@PostMapping("/unblockCard")
	private String createUserUnlockRequest(@RequestParam(name = "id") int accountId, Authentication authentication) {
		User user = userService.findByEmail(authentication.getName());
		UserAccount account = user.getAccounts().stream().filter(a -> a.getId() == accountId).findFirst().orElse(new UserAccount());
		String result = requestService.createUserUnlockRequest(user, account);
		return "redirect:/"+result;
	}
}
