package com.epam.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.payments.model.AccountStatus;
import com.epam.payments.model.Request;
import com.epam.payments.model.RequestStatus;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.model.statuses.UserAccountStatuses;
import com.epam.payments.model.statuses.UserRequestStatuses;
import com.epam.payments.repository.AccountStatusRepository;
import com.epam.payments.repository.UserRequestStatusRepository;
import com.epam.payments.repository.UserRequestsRepository;

@ExtendWith(MockitoExtension.class)
public class UserRequestsServiceTest {

	@Mock
	private UserRequestsRepository userRequestsRepository;
	@Mock
	private UserRequestStatusRepository requestStatusRepository;
	@Mock
	private AccountStatusRepository accountStatusRepository;
	private final int TEST_REQUEST_ID = 1;
	@InjectMocks
	UserRequestService userRequestService;
		
	@Test
	public void shouldChangeRequestStatusToClosed() {
		Request request = new Request();
		
		RequestStatus active = new RequestStatus();
		active.setId(UserRequestStatuses.ACTIVE.getId());
		
		RequestStatus closed = new RequestStatus();
		active.setId(UserRequestStatuses.CLOSED.getId());
		
		request.setStatus(active);
		
		when(userRequestsRepository.getById(TEST_REQUEST_ID)).thenReturn(request);
		when(requestStatusRepository.getById(UserRequestStatuses.CLOSED.getId())).thenReturn(closed);
		
		userRequestService.changeRequestStatusToClosed(TEST_REQUEST_ID);
		verify(userRequestsRepository, atLeastOnce()).save(request);
		assertEquals(request.getStatus(), closed);
	}
	
	@Test
	public void shouldAcceptUserRequestAndClose() {
		Request request = new Request();
		
		UserAccount account = new UserAccount();
		
		AccountStatus activeAccount = new AccountStatus();
		activeAccount.setId(UserAccountStatuses.ACTIVE.getId());
		
		AccountStatus blockedAccount = new AccountStatus();
		blockedAccount.setId(UserAccountStatuses.BLOCKED.getId());
		
		account.setStatus(blockedAccount);
		
		RequestStatus active = new RequestStatus();
		active.setId(UserRequestStatuses.ACTIVE.getId());
		
		RequestStatus closed = new RequestStatus();
		active.setId(UserRequestStatuses.CLOSED.getId());
		
		request.setAccount(account);
		request.setStatus(active);
		when(userRequestsRepository.getById(TEST_REQUEST_ID)).thenReturn(request);
		when(requestStatusRepository.getById(UserRequestStatuses.CLOSED.getId())).thenReturn(closed);
		when(accountStatusRepository.getById(UserAccountStatuses.ACTIVE.getId())).thenReturn(activeAccount);
		
		userRequestService.acceptUserRequest(TEST_REQUEST_ID);
		verify(userRequestsRepository, atLeastOnce()).save(request);
		assertEquals(request.getStatus(), closed);
		assertEquals(account.getStatus(), activeAccount);
	}
	
	@Test
	public void shouldCreateUserUnlockRequest() {
		User user = new User();
		UserAccount account = new UserAccount();
		
	
		RequestStatus active = new RequestStatus();
		active.setId(UserRequestStatuses.ACTIVE.getId());

		when(requestStatusRepository.getById(UserRequestStatuses.ACTIVE.getId())).thenReturn(active);
		when(userRequestsRepository.findByUserAndAccountAndStatus(user, account, active)).thenReturn(Optional.ofNullable(null));
		String actual  = userRequestService.createUserUnlockRequest(user, account);
		verify(userRequestsRepository, atLeastOnce()).save(any(Request.class));
		assertEquals("?operationStatus=unblockedRequestSent", actual);
	}
}
