package com.epam.payments.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epam.payments.model.AccountStatus;
import com.epam.payments.service.AccountsStatusService;

@Controller
public class AccountController {
	
	private AccountsStatusService accountsStatusService;
	
	@Autowired
	public AccountController(AccountsStatusService accountsStatusService) {
		this.accountsStatusService = accountsStatusService;
	}
	
	@GetMapping("/account")
	public String getAccountStatus(HttpServletRequest request, Model model) {
		AccountStatus status = accountsStatusService.findById(1);
		model.addAttribute("status", status);
		return "";
	}

	

}
