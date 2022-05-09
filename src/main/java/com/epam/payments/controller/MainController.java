package com.epam.payments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/")
public class MainController {
	
	@GetMapping("/")
	public String mainPage() {
		return "main";
	}
}
