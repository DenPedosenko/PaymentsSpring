package com.epam.payments.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.model.statuses.UserAccountStatuses;
import com.epam.payments.repository.AccountRepository;
import com.epam.payments.repository.AccountStatusRepository;

@Service
public class AccountService {
	
	private AccountRepository accountRepository;
	private AccountStatusRepository accountStatusRepository;
	
	@Autowired
	public AccountService(AccountRepository accountRepository, AccountStatusRepository accountStatusRepository) {
		this.accountRepository = accountRepository;
		this.accountStatusRepository = accountStatusRepository;
	}

	public UserAccount findById(int id) {
		return accountRepository.getById(id);
	}
	
	public void save(UserAccount account) {
		accountRepository.save(account);
	}
	
	public void increaseAccountBalance(int id, double amount) {
		UserAccount account = accountRepository.getById(id);
		account.setBalance(account.getBalance() + amount);
		accountRepository.save(account);
	}

	public void changeAccountStatusToBlocked(int id) {
		UserAccount account = accountRepository.getById(id);
		account.setStatus(accountStatusRepository.getById(UserAccountStatuses.BLOCKED.getId()));
		accountRepository.save(account);
	}
	
	public List<UserAccount> getActiveAccounts(User user){
		return user.getAccounts().stream().filter(account -> account.getStatus().getId() != UserAccountStatuses.BLOCKED.getId()).collect(Collectors.toList());
	}
	
	
}
