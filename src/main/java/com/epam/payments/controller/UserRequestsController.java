package com.epam.payments.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.payments.model.AccountStatus;
import com.epam.payments.model.Request;
import com.epam.payments.model.RequestStatus;
import com.epam.payments.model.UserAccount;
import com.epam.payments.model.statuses.UserAccountStatuses;
import com.epam.payments.model.statuses.UserRequestStatuses;
import com.epam.payments.repository.UserRequestsRepository;

@Controller
public class UserRequestsController {
	
	@Autowired
	private UserRequestsRepository userRequestsRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	@GetMapping("/dismissRequest")
	private String dismissRequest(@RequestParam int id) {
		Request request = userRequestsRepository.getById(id);
		request.setStatus(entityManager.getReference(RequestStatus.class, UserRequestStatuses.CLOSED.getId()));
		userRequestsRepository.save(request);
		return "redirect:/";	
	}
	
	@GetMapping("/acceptRequest")
	private String acceptRequest(@RequestParam int id) {
		Request request = userRequestsRepository.getById(id);
		UserAccount account = request.getAccount();
		account.setAccountStatus(entityManager.getReference(AccountStatus.class, UserAccountStatuses.ACTIVE.getId()));
		request.setStatus(entityManager.getReference(RequestStatus.class, UserRequestStatuses.CLOSED.getId()));
		userRequestsRepository.save(request);
		return "redirect:/";	
	}
}
