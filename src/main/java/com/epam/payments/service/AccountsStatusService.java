package com.epam.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.payments.model.AccountStatus;
import com.epam.payments.repository.AccountStatusRepository;

@Service
public class AccountsStatusService {
	private final AccountStatusRepository accountStatusRepository;
	
	@Autowired
	public AccountsStatusService(AccountStatusRepository accountStatusRepository) {
		this.accountStatusRepository = accountStatusRepository;
	}

	public AccountStatus findById(int id) {
		return accountStatusRepository.getById(id);
	}
}
