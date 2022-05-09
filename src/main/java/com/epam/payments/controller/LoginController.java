package com.epam.payments.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String showLoginPage(Authentication authentication) {
		return "login";
	}
	
	@GetMapping("/access-denied")
	public String  showAccessDenied() {
		return "access-denied";
	}
}
