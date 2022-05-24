package com.epam.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.PaymentType;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.PaymentRepository;
import com.epam.payments.repository.PaymentStatusRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentsServiceTest {

	@Mock
	private Model model;
	@Mock
	private PaymentRepository paymentRepository;
	@Mock
	private PaymentStatusRepository paymentStatusRepository;
	@InjectMocks
	private PaymentsServiceImpl paymentsService;

	@Test
	public void shouldReturnUserPaymentsColectedByDate() {
		User user = new User();

		Payment payment = new Payment();
		payment.setDate(LocalDateTime.of(2022, 5, 24, 16, 30));

		Payment payment1 = new Payment();
		payment1.setDate(LocalDateTime.of(2022, 5, 24, 16, 30));

		List<Payment> payments = List.of(payment, payment1);
		when(paymentRepository.findAllByUser(user)).thenReturn(payments);

		Map<String, List<Payment>> expected = new HashMap<>() {
			private static final long serialVersionUID = 1L;
			{
				put("2022-05-24", payments);
			}
		};
		Map<String, List<Payment>> actual = paymentsService.getUserPaymentsCollectedByDate(user);

		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnUserPaymentsByStatus() {
		User user = new User();

		PaymentStatus status = new PaymentStatus();

		Payment payment = new Payment();
		payment.setDate(LocalDateTime.of(2022, 5, 24, 16, 30));
		payment.setPaymentStatus(status);

		Payment payment1 = new Payment();
		payment1.setDate(LocalDateTime.of(2022, 5, 25, 16, 30));
		payment.setPaymentStatus(status);

		List<Payment> payments = List.of(payment, payment1);
		when(paymentRepository.findAllByUserAndPaymentStatus(user, status)).thenReturn(payments);

		Map<String, List<Payment>> expected = new HashMap<>() {
			private static final long serialVersionUID = 1L;
			{
				put("2022-05-25", List.of(payment1));
				put("2022-05-24", List.of(payment));
			}
		};
		Map<String, List<Payment>> actual = paymentsService.getUserPaymentsByStatus(user, status);

		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldCreateNewPayment() {
		User user = new User();
		UserAccount account = new UserAccount();
		PaymentType type = new PaymentType();
		account.setBalance(100);
		String actual = paymentsService.proccessNewPayment(user, account, type, 50);
		verify(paymentRepository, atLeastOnce()).save(any(Payment.class));
		assertEquals("?operationStatus=success", actual);
	}
	
	@Test
	public void shouldSavePayment() {
		User user = new User();
		UserAccount account = new UserAccount();
		PaymentType type = new PaymentType();
		account.setBalance(100);
		String actual = paymentsService.savePayment(user, account, type, 50);
		verify(paymentRepository, atLeastOnce()).save(any(Payment.class));
		assertEquals("?operationStatus=save", actual);
	}
	
	@Test
	public void shouldReturnErrorWhenCreateNewPaymentIfNotEnoughFunds() {
		User user = new User();
		UserAccount account = new UserAccount();
		PaymentType type = new PaymentType();
		account.setBalance(100);
		String actual = paymentsService.proccessNewPayment(user, account, type, 150);
		assertEquals("?operationStatus=error", actual);
	}
	
	@Test
	public void shouldReturnErrorWhenProccessSavedPaymentIfNotEnoughFunds() {
		UserAccount account = new UserAccount();
		account.setBalance(100);
		Payment payment = new Payment();
		payment.setUserAccount(account);
		payment.setAmount(150);
		String actual = paymentsService.proccessPayment(payment);
		assertEquals("?operationStatus=error", actual);
	}
	
	@Test
	public void shouldProccessSavedPayment() {
		UserAccount account = new UserAccount();
		account.setBalance(100);
		Payment payment = new Payment();
		payment.setUserAccount(account);
		payment.setAmount(50);
		String actual = paymentsService.proccessPayment(payment);
		assertEquals("?operationStatus=success", actual);
	}

}
