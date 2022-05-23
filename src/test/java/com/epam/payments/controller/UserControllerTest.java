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
import org.springframework.ui.Model;

import com.epam.payments.model.User;
import com.epam.payments.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;
	@Mock
	private Model model;
	
	@InjectMocks
	private UsersController controller;
	
	private final int TEST_USER_ID = 1;
		
	@Test
	public void shouldReturnUsers() {
		Page<User> users = new PageImpl<User>(List.of(new User(), new User()));
		Pageable pageable = PageRequest.of(0, 10, Sort.by("lastName"));
		when(userService.findAllUsers(pageable)).thenReturn(users);
		
		String actual = controller.getUsers(model, "true", "lastName", 1);
		verify(userService, atMostOnce()).findAllUsers(pageable);
		verify(model, atLeast(6)).addAttribute(anyString(), any());
		assertEquals("users", actual);
	}
	
	@Test
	public void shouldBlockUsers() {
		String actual = controller.blockUser(TEST_USER_ID);
		verify(userService, atMostOnce()).blockUser(TEST_USER_ID);
		assertEquals("redirect:/users", actual);
	}
	
	@Test
	public void shouldUnlockUsers() {
		String actual = controller.unlockUser(TEST_USER_ID);
		verify(userService, atMostOnce()).unlockUser(TEST_USER_ID);
		assertEquals("redirect:/users", actual);
	}
	

}
