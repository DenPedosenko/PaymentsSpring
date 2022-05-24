package com.epam.payments.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.epam.payments.model.Card;
import com.epam.payments.model.Payment;
import com.epam.payments.model.Request;
import com.epam.payments.model.User;
import com.epam.payments.model.statuses.PaymentsStatuses;
import com.epam.payments.model.statuses.UserRequestStatuses;
import com.epam.payments.repository.PaymentStatusRepository;
import com.epam.payments.repository.UserRepository;
import com.epam.payments.repository.UserRequestStatusRepository;
import com.epam.payments.repository.UserRequestsRepository;

@Service
public class MainPagePrepearerService {
	
	private PaymentStatusRepository paymentStatusRepository;
	private UserRequestsRepository userRequestsRepository;
	private UserRequestStatusRepository userRequestStatusRepository;
	private UserRepository userRepository;
	private PaymentsService paymentsService;
	
	@Autowired
	public MainPagePrepearerService(UserRequestsRepository userRequestsRepository, UserRepository userRepository,
			PaymentsService paymentsService, PaymentStatusRepository paymentStatusRepository, UserRequestStatusRepository userRequestStatusRepository) {
		this.userRequestsRepository = userRequestsRepository;
		this.userRepository = userRepository;
		this.paymentsService = paymentsService;
		this.paymentStatusRepository = paymentStatusRepository;
		this.userRequestStatusRepository = userRequestStatusRepository;
	}
	
	public void prepearUserMain(Model model, String email) {
		User user = userRepository.findByEmail(email);
		List<Card> cards = user.getAccounts().stream().map((v)-> v.getCard()).collect(Collectors.toList());
		Map<String, List<Payment>> payments = paymentsService.getUserPaymentsByStatus(user, paymentStatusRepository.getById(PaymentsStatuses.SENT.getId()));
		model.addAttribute("cards", cards);
		model.addAttribute("payments", payments);
		model.addAttribute("locale", LocaleContextHolder.getLocale());
	}

	public void prepearAdminMain(Model model) {
		List<Request> requests = userRequestsRepository.findAllByStatus(
				userRequestStatusRepository.getById(UserRequestStatuses.ACTIVE.getId()));
		model.addAttribute("requests", requests);
		model.addAttribute("locale", LocaleContextHolder.getLocale());
	}



}
