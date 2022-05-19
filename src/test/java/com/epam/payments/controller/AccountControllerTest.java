package com.epam.payments.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.epam.payments.model.UserAccount;
import com.epam.payments.service.AccountService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountControllerTest {
	
	@Mock
	AccountService accountService;
	
	@InjectMocks
	AccountController accountController;
	
	@Test
	public void shouldChangeAccountBalance() {
		UserAccount account = new UserAccount();
		account.setBalance(100.0);
		when(accountService.findById(1)).thenReturn(account);
		
		String actual = accountController.addFunds(100.0, 1);
		
		verify(accountService, atLeastOnce()).increaseAccountBalance(1, 100.0);

		assertEquals("redirect:/", actual);
	}
	
	
}
