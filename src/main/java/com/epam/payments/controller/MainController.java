package com.epam.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epam.payments.service.MainPagePrepearerService;

@Controller(value = "/")
public class MainController {
	private MainPagePrepearerService prepearService;
	
	@Autowired	
	public MainController(MainPagePrepearerService prepearService) {
		this.prepearService = prepearService;
	}

	@GetMapping("/")
	public String mainPage(Model model, Authentication authentication) {
		if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_Admin"))) {
			prepearService.prepearAdminMain(model);
		} else {
			prepearService.prepearUserMain(model, authentication.getName());
		}
		return "main";
	}

}
