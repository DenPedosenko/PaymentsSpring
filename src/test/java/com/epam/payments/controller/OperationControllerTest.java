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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.epam.payments.model.Payment;
import com.epam.payments.model.User;
import com.epam.payments.repository.PaymentRepository;
import com.epam.payments.service.PaymentsService;
import com.epam.payments.service.UserService;

@ExtendWith(MockitoExtension.class)
public class OperationControllerTest {
	@Mock
	Model model;
	@Mock
	Authentication authentication;
	@Mock
	private PaymentsService paymentsService;
	@Mock
	private PaymentRepository paymentRepository;
	@Mock
	private UserService userService;
	@InjectMocks
	private OperationsController operationController;
	
	private final int PAGE_COUNT = 1;
	private final int TEST_PAYMENT_ID = 1;
	
	
	@Test
	public void shouldReturnOperations() {
		User user = new User();
		Page<Payment> payments = new PageImpl<Payment>( List.of(new Payment(), new Payment()));
		when(paymentRepository.findAllByUser(any(), any())).thenReturn(payments);
		when(authentication.getName()).thenReturn("");
		when(userService.findByEmail(anyString())).thenReturn(user);
		
		String actual = operationController.getOperations(model, "true", "paymentStatus", PAGE_COUNT, authentication);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("paymentStatus"));
		verify(paymentRepository, atLeastOnce()).findAllByUser(user, pageable);
		verify(model, atLeast(5)).addAttribute(anyString(), any());
		assertEquals("operations", actual);
	}
	
	@Test
	public void shouldPayPayment() {
		Payment payment = new Payment();
		when(paymentRepository.getById(TEST_PAYMENT_ID)).thenReturn(payment);
		when(paymentsService.proccessPayment(payment)).thenReturn("?operationStatus=success");
		String actual = operationController.payPayment(TEST_PAYMENT_ID);
		verify(paymentRepository, atLeastOnce()).getById(TEST_PAYMENT_ID);
		verify(paymentsService, atLeastOnce()).proccessPayment(payment);
		
		assertEquals("redirect:/operations?operationStatus=success", actual);
	}

}
