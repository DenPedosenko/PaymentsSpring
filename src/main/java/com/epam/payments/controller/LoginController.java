package com.epam.payments.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	    @RequestMapping("/login")
	    public String user(Authentication authentication) {
	        return "login";
	    }
}
