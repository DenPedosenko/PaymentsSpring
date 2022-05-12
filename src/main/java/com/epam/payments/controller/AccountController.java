package com.epam.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.payments.model.UserAccount;
import com.epam.payments.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/addFunds")
	public String getAccountStatus(Double amount, @RequestParam int id) {
		UserAccount account = accountService.findById(id);
		account.setBalance(account.getBalance() + amount);
		accountService.save(account);
		return "redirect:/";
	}
}
