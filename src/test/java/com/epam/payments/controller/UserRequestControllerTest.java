package com.epam.payments.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMostOnce;
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

import com.epam.payments.model.Request;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.service.UserRequestService;
import com.epam.payments.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserRequestControllerTest {
	@Mock
	private UserRequestService requestService;
	@Mock
	private UserService userService;
	@Mock
	Model model;
	@Mock
	Authentication authentication;
	
	@InjectMocks
	UserRequestsController requestsController;
	
	private final int TEST_REQUEST_ID = 1;
	private final int TEST_ACCOUNT_ID = 1;
	
	@Test
	public void shouldReturnRequests() {
		Page<Request> requests = new PageImpl<Request>( List.of(new Request(), new Request()));
		Pageable pageable = PageRequest.of(0, 10, Sort.by("status"));
		when(requestService.findAll(pageable)).thenReturn(requests);
		
		String actual = requestsController.getRequests(model, "true", "status", 1);
		
		verify(requestService, atMostOnce()).findAll(pageable);
		verify(model, atLeast(6)).addAttribute(anyString(), any());
		assertEquals("requests", actual);
	}
	
	@Test
	public void shouldAcceptUserUnlockRequest() {
		String actual = requestsController.acceptUserUnlockRequest(TEST_REQUEST_ID);	
		
		verify(requestService, atMostOnce()).acceptUserRequest(TEST_REQUEST_ID);
		assertEquals("redirect:/", actual);
	}
	
	@Test
	public void shouldDismissUserUnlockRequest() {
		String actual = requestsController.dismissUserUnlockRequest(TEST_REQUEST_ID);	
		
		verify(requestService, atMostOnce()).changeRequestStatusToClosed(TEST_REQUEST_ID);
		assertEquals("redirect:/", actual);
	}
	@Test
	public void shouldAcceptUserUnlockRequestFromRequestPage() {
		String actual = requestsController.acceptUserUnlockRequestFromRequestPage(TEST_REQUEST_ID);	
		
		verify(requestService, atMostOnce()).acceptUserRequest(TEST_REQUEST_ID);
		assertEquals("redirect:/requests", actual);
	}
	
	@Test
	public void shouldDismissUserUnlockRequestFromRequestPage() {
		String actual = requestsController.dismissUserUnlockRequestFromRequestPage(TEST_REQUEST_ID);	
		
		verify(requestService, atMostOnce()).changeRequestStatusToClosed(TEST_REQUEST_ID);
		assertEquals("redirect:/requests", actual);
	}
	
	@Test
	public void shouldCreateUserUnlockRequest() {
		User user = new User();
		UserAccount account = new UserAccount();
		account.setId(TEST_ACCOUNT_ID);
		user.setAccounts(List.of(account));
		when(authentication.getName()).thenReturn("");
		when(userService.findByEmail("")).thenReturn(user);
		when(requestService.createUserUnlockRequest(user, account)).thenReturn("?operationStatus=unblockedRequestSent");
		String actual = requestsController.createUserUnlockRequest(TEST_ACCOUNT_ID, authentication);
		assertEquals("redirect:/?operationStatus=unblockedRequestSent", actual);
	}

}
