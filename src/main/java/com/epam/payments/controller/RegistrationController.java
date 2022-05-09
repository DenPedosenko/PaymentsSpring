package com.epam.payments.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.epam.payments.model.User;
import com.epam.payments.model.UserStatus;
import com.epam.payments.model.UserType;
import com.epam.payments.service.SecurityService;
import com.epam.payments.service.UserService;

@Controller
public class RegistrationController {
	@PersistenceContext
	private EntityManager entityManager;
	
	private final UserService userService;
	private final SecurityService securityService;
	
	@Autowired
	public RegistrationController(UserService userService, SecurityService securityService) {
		this.userService = userService;
		this.securityService = securityService;
	}
	
	@GetMapping("/registration")
    public String registrateForm(Model model) {
		model.addAttribute("user", new User());
        return "registration";
    }
	
	

	@PostMapping("/registration")
	public String registrateUser(@Valid @ModelAttribute("user") User newUser, BindingResult result){
		 if (result.hasErrors()) {
	            return "registration";
	    }
		
		newUser.setUserStatus(entityManager.getReference(UserStatus.class, 1));
		newUser.setUserType(entityManager.getReference(UserType.class, 1));
		userService.saveUser(newUser);
		securityService.autoLogin(newUser.getEmail(), newUser.getPassword());
		
		return "redirect:/";
		
	}

}
