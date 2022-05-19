package com.epam.payments.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.epam.payments.model.Request;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.model.statuses.UserAccountStatuses;
import com.epam.payments.model.statuses.UserRequestStatuses;
import com.epam.payments.repository.AccountStatusRepository;
import com.epam.payments.repository.UserRequestStatusRepository;
import com.epam.payments.repository.UserRequestsRepository;

@Service
public class UserRequestService {

	private UserRequestsRepository userRequestsRepository;
	private UserRequestStatusRepository requestStatusRepository;
	private AccountStatusRepository accountStatusRepository;

	public UserRequestService(UserRequestsRepository userRequestsRepository,
			UserRequestStatusRepository requestStatusRepository, AccountStatusRepository accountStatusRepository) {
		this.userRequestsRepository = userRequestsRepository;
		this.requestStatusRepository = requestStatusRepository;
		this.accountStatusRepository = accountStatusRepository;
	}

	public void changeRequestStatusToClosed(int id) {
		Request request = userRequestsRepository.getById(id);
		request.setStatus(requestStatusRepository.getById(UserRequestStatuses.CLOSED.getId()));
		userRequestsRepository.save(request);
	}

	public void acceptUserRequest(int id) {
		Request request = userRequestsRepository.getById(id);
		UserAccount account = request.getAccount();
		account.setStatus(accountStatusRepository.getById(UserAccountStatuses.ACTIVE.getId()));
		request.setStatus(requestStatusRepository.getById(UserRequestStatuses.CLOSED.getId()));
		userRequestsRepository.save(request);
	}

	public String createUserUnlockRequest(User user, UserAccount account) {
		Optional<Request> existing = userRequestsRepository.findByUserAndAccountAndStatus(user, account,
				requestStatusRepository.getById(UserRequestStatuses.ACTIVE.getId()));
		if (existing.isPresent()) {
			return "?operationStatus=alreadyCreated";
		}
		Request request = new Request();
		request.setDate(LocalDateTime.now());
		request.setStatus(requestStatusRepository.getById(UserRequestStatuses.ACTIVE.getId()));
		request.setAccount(account);
		request.setUser(user);
		userRequestsRepository.save(request);
		return "?operationStatus=unblockedRequestSent";
	}
}
