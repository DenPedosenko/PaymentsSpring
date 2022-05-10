package com.epam.payments.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.epam.payments.model.User;
import com.epam.payments.service.CustomUserDetailsService;
import com.epam.payments.service.SecurityService;

@Controller
public class RegistrationController {
	private final SecurityService securityService;
	private final CustomUserDetailsService userDetailsService;
	
	@Autowired
	public RegistrationController(SecurityService securityService, CustomUserDetailsService userDetailsService) {
		this.securityService = securityService;
		this.userDetailsService = userDetailsService;
	}

	@GetMapping("/registration")
	public String registrateForm(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/processRegistration")
	public String processRegistration(@Valid @ModelAttribute("user") User newUser, BindingResult result, Model model, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "registration";
		}
		User existing = userDetailsService.findByEmail(newUser.getEmail());
		if (existing != null) {
			model.addAttribute("user", new User());
			return "redirect:registration?error=UAE";
		}
		userDetailsService.save(newUser);
		securityService.autoLogin(request, newUser.getEmail(), newUser.getPassword());

		return "redirect:/";

	}

}
