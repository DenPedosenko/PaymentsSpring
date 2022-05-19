package com.epam.payments.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OperationsController {
	
	@GetMapping("/operations")
	public String getOperations(Model model, Authentication authentication) {
		model.addAttribute("locale", LocaleContextHolder.getLocale());
		model.addAttribute("page", 1);
		model.addAttribute("size", 12);
		return "operations";
	}

}
