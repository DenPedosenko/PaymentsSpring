package com.epam.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.AccountRepository;
import com.epam.payments.repository.AccountStatusRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private AccountStatusRepository accountStatusRepository;
	
	@InjectMocks
	AccountService accountService;
	
	@Test
	public void shoulIncreaseAccountBalance() {
		UserAccount account = new UserAccount();
		account.setBalance(100);
		when(accountService.findById(1)).thenReturn(account);
		accountService.increaseAccountBalance(1, 100);
		
		verify(accountRepository, atLeastOnce()).getById(1);
		verify(accountRepository).save(account);
		assertEquals(200, account.getBalance());
	}

}
