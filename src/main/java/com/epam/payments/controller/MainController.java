package com.epam.payments.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epam.payments.model.Request;


@Controller(value = "/")
public class MainController {
	
	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("requests", List.of(new Request(), new Request()));
		return "main";
	}
}
