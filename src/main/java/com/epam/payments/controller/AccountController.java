package com.epam.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.payments.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/addFunds")
	public String addFunds(Double amount, @RequestParam int id) {
		accountService.increaseAccountBalance(id, amount);
		return "redirect:/";
	}
	
	@PostMapping("/blockCard")
	public String blockUserAccount(@RequestParam int id) {
		accountService.changeAccountStatusToBlocked(id);
		return "redirect:/";
	}
}
