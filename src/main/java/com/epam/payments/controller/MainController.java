package com.epam.payments.controller;

import java.text.Collator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.epam.payments.model.Card;
import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.Request;
import com.epam.payments.model.RequestStatus;
import com.epam.payments.model.User;
import com.epam.payments.model.statuses.PaymentsStatuses;
import com.epam.payments.model.statuses.UserRequestStatuses;
import com.epam.payments.repository.UserRepository;
import com.epam.payments.repository.UserRequestsRepository;
import com.epam.payments.service.PaymentsService;

@Controller(value = "/")
public class MainController {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UserRequestsRepository userRequestsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentsService paymentsService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@GetMapping("/")
	public String mainPage(Model model, Authentication authentication) {
		if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_Admin"))) {
			List<Request> requests = userRequestsRepository.findAllByStatus(
					entityManager.getReference(RequestStatus.class, UserRequestStatuses.ACTIVE.getId()));
			model.addAttribute("requests", requests);
		} else {
			User user = userRepository.findByEmail(authentication.getName());
			List<Card> cards = user.getAccounts().stream().map((v)-> v.getCard()).collect(Collectors.toList());
			Map<String, List<Payment>> payments = paymentsService.getUserPaymentsByStatus(user, entityManager.getReference(PaymentStatus.class, PaymentsStatuses.SENT.getId()));
			model.addAttribute("cards", cards);
			model.addAttribute("payments", payments);
		}
		return "main";
	}
}
