package com.epam.payments.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.AccountRepository;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.UserService;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountControllerTest {
	
	@Mock
	Model model;
	@Mock
	Authentication authentication;
	
	@Mock
	AccountService accountService;
	@Mock
	AccountRepository accountRepository;
	@Mock
	UserService userService;
	
	@InjectMocks
	AccountController accountController;
	
	@Test
	public void shouldChangeAccountBalance() {
		String actual = accountController.addFunds(100.0, 1);	
		verify(accountService, atLeastOnce()).increaseAccountBalance(1, 100.0);
		assertEquals("redirect:/", actual);
	}
	
	@Test
	public void shouldBlockUserAccount() {
		String actual = accountController.blockAccount(1);	
		verify(accountService, atLeastOnce()).blockAccount(1);
		assertEquals("redirect:/users", actual);
	}
	
	@Test
	public void shouldBlockUserAccountPost() {
		String actual = accountController.blockUserAccount(1);	
		verify(accountService, atLeastOnce()).blockAccount(1);
		assertEquals("redirect:/", actual);
	}
	
	@Test
	public void shouldUnlockUserAccount() {
		String actual = accountController.unlockAccount(1);	
		verify(accountService, atLeastOnce()).unlockAccount(1);
		assertEquals("redirect:/users", actual);
	}
	
	@Test
	public void shouldReturnAccounts() {
		User user = new User();
		List<UserAccount> accounts = List.of(new UserAccount(), new UserAccount());
		when(accountRepository.findAllByUser(any(), any())).thenReturn(accounts);
		when(authentication.getName()).thenReturn("");
		when(userService.findByEmail(anyString())).thenReturn(user);
		
		String actual = accountController.getAccounts(model, authentication, "true", "balance");
		verify(accountRepository, atLeastOnce()).findAllByUser(user, Sort.by("balance"));
		verify(model, atLeast(4)).addAttribute(anyString(), any());
		assertEquals("accounts", actual);
	}
	
	
}
