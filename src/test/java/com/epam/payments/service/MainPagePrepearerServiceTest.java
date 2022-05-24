package com.epam.payments.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.epam.payments.model.Card;
import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.Request;
import com.epam.payments.model.RequestStatus;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.repository.PaymentStatusRepository;
import com.epam.payments.repository.UserRepository;
import com.epam.payments.repository.UserRequestStatusRepository;
import com.epam.payments.repository.UserRequestsRepository;

@ExtendWith(MockitoExtension.class)
public class MainPagePrepearerServiceTest {
	@Mock
	Model model;
	@Mock
	private PaymentStatusRepository paymentStatusRepository;
	@Mock
	private UserRequestsRepository userRequestsRepository;
	@Mock
	private UserRequestStatusRepository userRequestStatusRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PaymentsService paymentsService;
	@InjectMocks
	MainPagePrepearerService prepearer;
	
	@Test
	public void shouldPrepearUSerMainPage() {
		User user = new User();
		UserAccount account =  new UserAccount();
		Card card = new Card();
		List<Card> cards = List.of(card);
		account.setCard(card);
		user.setAccounts(List.of(account));
		Map<String, List<Payment>> payments = new HashMap<String, List<Payment>>();
		PaymentStatus status = new PaymentStatus();
		when(paymentStatusRepository.getById(anyInt())).thenReturn(status);
		when(userRepository.findByEmail("")).thenReturn(user);
		when(paymentsService.getUserPaymentsByStatus(user, status)).thenReturn(payments);
		
		prepearer.prepearUserMain(model, "");
		
		verify(model, atLeast(3)).addAttribute(anyString(), any());
		verify(model, atLeast(1)).addAttribute("payments", payments);
		verify(model, atLeast(1)).addAttribute("cards", cards);
	}
	
	@Test
	public void shouldPrepearAdminMainPage() {
		
		Request request =  new Request();
		List<Request> requests = List.of(request);
		RequestStatus status = new RequestStatus();
		when(userRequestStatusRepository.getById(anyInt())).thenReturn(status);
		when(userRequestsRepository.findAllByStatus(status)).thenReturn(requests);
		
		prepearer.prepearAdminMain(model);
		
		verify(model, atLeast(2)).addAttribute(anyString(), any());
		verify(model, atLeast(1)).addAttribute("requests", requests);
	}
	

}
