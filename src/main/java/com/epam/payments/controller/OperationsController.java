package com.epam.payments.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.payments.model.Payment;
import com.epam.payments.repository.PaymentRepository;
import com.epam.payments.service.PaymentsService;
import com.epam.payments.service.UserService;


@Controller
public class OperationsController {

	private PaymentsService paymentsService;
	private PaymentRepository paymentRepository;
	private UserService userService;

	@Autowired
	public OperationsController(PaymentsService paymentsService, PaymentRepository paymentRepository, UserService userService) {
		this.paymentsService = paymentsService;
		this.paymentRepository = paymentRepository;
		this.userService = userService;
	}

	@GetMapping(value = "/operations")
	public String getOperations(Model model, @RequestParam(required = false, defaultValue = "true") String ascending,
			@RequestParam(defaultValue = "paymentStatus", required = false) String orderBy,
			@RequestParam(defaultValue = "1", required = false) Integer page, Authentication authentication) {
		Pageable pageable = PageRequest.of(page - 1, 10, ascending.equals("true")?Sort.by(orderBy):Sort.by(orderBy).descending());
		Page<Payment> operations = paymentRepository.findAllByUser(userService.findByEmail(authentication.getName()),pageable);
		model.addAttribute("locale", LocaleContextHolder.getLocale());
		model.addAttribute("page", page);
		model.addAttribute("operations", operations.toList());
		model.addAttribute("size", operations.getTotalPages());
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("ascending", ascending);
		return "operations";
	}
	

	@GetMapping(value = "/operations/pay")
	public String payPayment(@RequestParam Integer id) {
		Payment payment = paymentRepository.getById(id);
		String result = paymentsService.proccessPayment(payment);
		return "redirect:/operations"+result;
		
	}
}
