package com.epam.payments.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

	private LoginController controller = new LoginController();
	
	@Test
	public void shouldReturnLoginPage() {
		String actual = controller.showLoginPage();
		assertEquals("login", actual);
	}
	@Test
	public void shouldReturnAccessDeniedPage() {
		String actual = controller.showAccessDenied();
		assertEquals("access-denied", actual);
	}
}
