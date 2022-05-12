package com.epam.payments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public UserAccount findById(int id) {
		return accountRepository.getById(id);
	}
	
	public void save(UserAccount account) {
		accountRepository.save(account);
	}
}
