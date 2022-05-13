package com.epam.payments.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

@Service
public class MainPagePrepearerService {
	@PersistenceContext
	private EntityManager entityManager;
	
	private UserRequestsRepository userRequestsRepository;
	private UserRepository userRepository;
	private PaymentsService paymentsService;
	
	@Autowired
	public MainPagePrepearerService(UserRequestsRepository userRequestsRepository, UserRepository userRepository,
			PaymentsService paymentsService) {
		this.userRequestsRepository = userRequestsRepository;
		this.userRepository = userRepository;
		this.paymentsService = paymentsService;
	}
	
	public void prepearUserMain(Model model, String email) {
		User user = userRepository.findByEmail(email);
		List<Card> cards = user.getAccounts().stream().map((v)-> v.getCard()).collect(Collectors.toList());
		Map<String, List<Payment>> payments = paymentsService.getUserPaymentsByStatus(user, entityManager.getReference(PaymentStatus.class, PaymentsStatuses.SENT.getId()));
		model.addAttribute("cards", cards);
		model.addAttribute("payments", payments);
	}

	public void prepearAdminMain(Model model) {
		List<Request> requests = userRequestsRepository.findAllByStatus(
				entityManager.getReference(RequestStatus.class, UserRequestStatuses.ACTIVE.getId()));
		model.addAttribute("requests", requests);
	}



}
