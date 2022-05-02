package com.epam.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epam.payments.repository.PaymentRepository;

@Controller
public class PaymentsController {
	
	private PaymentRepository paymentRepository;

	@Autowired
	public PaymentsController(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	@GetMapping("/payments")
	public String getPayments(Model model) {
		
		return "";
	}
	

}
