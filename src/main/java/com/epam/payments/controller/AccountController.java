package com.epam.payments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.AccountRepository;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.UserService;

@Controller
public class AccountController {

	private AccountService accountService;
	private AccountRepository accountRepository;
	private UserService userService;

	@Autowired
	public AccountController(AccountService accountService, AccountRepository accountRepository,
			UserService userService) {
		this.accountService = accountService;
		this.accountRepository = accountRepository;
		this.userService = userService;
	}

	@GetMapping("/accounts")
	public String getAccounts(Model model, Authentication authentication,
			@RequestParam(required = false, defaultValue = "true") String ascending,
			@RequestParam(defaultValue = "balance", required = false) String orderBy) {
		List<UserAccount> accounts = accountRepository.findAllByUser(userService.findByEmail(authentication.getName()),
				ascending.equals("true") ? Sort.by(orderBy) : Sort.by(orderBy).descending());
		model.addAttribute("locale", LocaleContextHolder.getLocale());
		model.addAttribute("accounts", accounts);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("ascending", ascending);

		return "accounts";
	}

	@PostMapping("/addFunds")
	public String addFunds(Double amount, @RequestParam int id) {
		accountService.increaseAccountBalance(id, amount);
		return "redirect:/";
	}

	@PostMapping("/blockCard")
	public String blockUserAccount(@RequestParam int id) {
		accountService.blockAccount(id);
		return "redirect:/";
	}
	
	@GetMapping("users/blockAccount")
	public String blockAccount(@RequestParam Integer id) {
		accountService.blockAccount(id);
		return "redirect:/users";
	}
	@GetMapping("users/unlockAccount")
	public String unlockAccount(@RequestParam Integer id) {
		accountService.unlockAccount(id);
		return "redirect:/users";
	}
}
