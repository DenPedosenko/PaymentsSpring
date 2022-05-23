package com.epam.payments.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.epam.payments.model.PaymentType;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.PaymentTypeRepository;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.PaymentsService;
import com.epam.payments.service.UserService;

@ExtendWith(MockitoExtension.class)
public class PaymentsControllerTest {
	@Mock
	Model model;
	@Mock
	Authentication authentication;
	@Mock
	HttpServletRequest request;
	@Mock
	private PaymentsService paymentsService;
	@Mock
	private UserService userService;
	@Mock
	private PaymentTypeRepository paymentTypeRepository;
	@Mock
	private AccountService accountService;
	@InjectMocks
	PaymentsController paymentsController;
	
	private final int TEST_ACCOUNT_ID = 1;
	private final int TEST_PAYMENT_ID = 1;
	private final double TEST_AMOUNT = 100.0;
	
	@Test
	public void shouldReturnPayments() {
		User user = new User();
		List<UserAccount> accounts = List.of(new UserAccount());
		when(authentication.getName()).thenReturn("");
		when(userService.findByEmail("")).thenReturn(user);
		when(accountService.getActiveAccounts(user)).thenReturn(accounts);
		
		String actual  = paymentsController.getPayments(model, authentication);
		verify(model, atLeast(3)).addAttribute(anyString(), any());
		
		assertEquals("payments", actual);
	}
	
	@Test
	public void shouldRepeatPayment() {
		User user = new User();
		UserAccount account = new UserAccount();
		account.setId(TEST_ACCOUNT_ID);
		PaymentType type = new PaymentType();
		List<UserAccount> accounts = List.of(account);
		user.setAccounts(accounts);
		when(paymentTypeRepository.getById(TEST_PAYMENT_ID)).thenReturn(type);
		when(authentication.getName()).thenReturn("");
		when(userService.findByEmail("")).thenReturn(user);
		when(request.getParameter("continue")).thenReturn("");
		when(request.getParameter("save")).thenReturn(null);
		when(paymentsService.proccessNewPayment(user, account, type, TEST_AMOUNT)).thenReturn("?operationStatus=success");
		
		String actual = paymentsController.repeatPayment(TEST_ACCOUNT_ID, TEST_PAYMENT_ID, TEST_AMOUNT, request, authentication);
		assertEquals("redirect:/?operationStatus=success", actual);
	}
	@Test
	public void shouldSavePayment() {
		User user = new User();
		UserAccount account = new UserAccount();
		account.setId(TEST_ACCOUNT_ID);
		PaymentType type = new PaymentType();
		List<UserAccount> accounts = List.of(account);
		user.setAccounts(accounts);
		when(paymentTypeRepository.getById(TEST_PAYMENT_ID)).thenReturn(type);
		when(authentication.getName()).thenReturn("");
		when(userService.findByEmail("")).thenReturn(user);
		when(request.getParameter("save")).thenReturn("");
		when(paymentsService.savePayment(user, account, type, TEST_AMOUNT)).thenReturn("?operationStatus=save");
		
		String actual = paymentsController.makePayment(TEST_ACCOUNT_ID, TEST_PAYMENT_ID, TEST_AMOUNT, request, authentication);
		assertEquals("redirect:/payments?operationStatus=save", actual);
	}
}
