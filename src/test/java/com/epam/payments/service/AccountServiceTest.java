package com.epam.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.payments.model.AccountStatus;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.model.statuses.UserAccountStatuses;
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
	
	private static AccountStatus activeAccountStatus = new AccountStatus();
	private static AccountStatus blockedAccountStatus = new AccountStatus();
	private static UserAccount activeAccount = new UserAccount();
	private static UserAccount blockedAccount = new UserAccount();
	
	@BeforeAll
	public static void  setup() {
		activeAccountStatus.setId(UserAccountStatuses.ACTIVE.getId());
		blockedAccountStatus.setId(UserAccountStatuses.BLOCKED.getId());
	}
	
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
	
	
	@Test
	public void shouldReturnActiveAccounts() {
		User user = new User();
		activeAccount.setStatus(activeAccountStatus);
		blockedAccount.setStatus(blockedAccountStatus);
		List<UserAccount> accounts = List.of(activeAccount, blockedAccount);
		user.setAccounts(accounts);
		List<UserAccount> actual = accountService.getActiveAccounts(user);
		List<UserAccount> expected = List.of(activeAccount);
		assertEquals(actual, expected);
	}
	
	@Test
	public void shouldBlockAccount() {
		when(accountRepository.getById(1)).thenReturn(activeAccount);
		when(accountStatusRepository.getById(2)).thenReturn(blockedAccountStatus);
		
		accountService.blockAccount(1);
		verify(accountRepository).getById(1);
		verify(accountStatusRepository).getById(2);
		
		assertEquals(activeAccount.getStatus(), blockedAccountStatus);
	}
	
	@Test
	public void shouldUnlockAccount() {
		when(accountRepository.getById(1)).thenReturn(blockedAccount);
		when(accountStatusRepository.getById(1)).thenReturn(activeAccountStatus);
		
		accountService.unlockAccount(1);
		verify(accountRepository).getById(1);
		verify(accountStatusRepository).getById(1);
		
		assertEquals(blockedAccount.getStatus(), activeAccountStatus);
	}
}
