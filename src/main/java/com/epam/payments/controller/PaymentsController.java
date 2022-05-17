package com.epam.payments.controller;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.epam.payments.model.PaymentType;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.PaymentRepository;
import com.epam.payments.repository.PaymentTypeRepository;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.PaymentsService;
import com.epam.payments.service.UserService;

@Controller
public class PaymentsController {

	private PaymentsService paymentsService;
	private UserService userService;
	private PaymentTypeRepository paymentTypeRepository;
	private AccountService accountService;

	@Autowired
	public PaymentsController(PaymentRepository paymentRepository, PaymentsService paymentsService,
			UserService userService, PaymentTypeRepository paymentTypeRepository, AccountService accountService) {
		this.paymentsService = paymentsService;
		this.userService = userService;
		this.paymentTypeRepository = paymentTypeRepository;
		this.accountService = accountService;
	}

	@GetMapping("/payments")
	public String getPayments(Model model, Authentication authentication) {
		List<UserAccount> accounts = accountService
				.getActiveAccounts(userService.findByEmail(authentication.getName()));
		model.addAttribute("locale", LocaleContextHolder.getLocale());
		model.addAttribute("payments", paymentTypeRepository.findAll());
		model.addAttribute("accounts", accounts);
		return "payments";
	}

	@PostMapping("/repeatPayment")
	public String repeatPayment(Integer accountId, Integer paymentTypeId, Double amount, HttpServletRequest req,
			Authentication authentication) {
		String result = processPayment(accountId, paymentTypeId, amount, req, authentication);
		return "redirect:/" + result;
	}

	@PostMapping("/makePayments")
	public String makePayment(Integer accountId, Integer paymentTypeId, Double amount, HttpServletRequest req,
			Authentication authentication) {

		String result = processPayment(accountId, paymentTypeId, amount, req, authentication);
		return "redirect:/payments" + result;
	}

	public String processPayment(Integer accountId, Integer paymentTypeId, Double amount, HttpServletRequest req,
			Authentication authentication) {
		String result = "";
		User user = userService.findByEmail(authentication.getName());
		PaymentType paymentType = paymentTypeRepository.getById(paymentTypeId);
		UserAccount account = user.getAccounts().stream().filter(a -> a.getId() == accountId).findFirst()
				.orElse(new UserAccount());

		if (req.getParameter("save") != null) {
			result = paymentsService.savePayment(user, account, paymentType, amount);
		} else if (req.getParameter("continue") != null) {
			result = paymentsService.proccessPayment(user, account, paymentType, amount);
		}
		return result;
	}

}
