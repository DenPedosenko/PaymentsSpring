package com.epam.payments.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.epam.payments.model.User;
import com.epam.payments.service.CustomUserDetailsService;
import com.epam.payments.service.SecurityService;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {
	@Mock
	BindingResult result;
	@Mock
	private  SecurityService securityService;
	@Mock
	private  CustomUserDetailsService userDetailsService;
	@Mock
	private Model model;
	@Mock
	HttpServletRequest request;
	
	@InjectMocks
	RegistrationController registrationController;
	
	@Test
	public void shoulReturnRegistratin() {
		verify(model, atMostOnce()).addAttribute("user", new User());
		String actual = registrationController.registrateForm(model);
		assertEquals("registration", actual);
	}
	
	@Test
	public void shouldHandleBindingError() {
		when(result.hasErrors()).thenReturn(true);
		String actual = registrationController.processRegistration(null, result, model, request);
		assertEquals("registration", actual);		
	}
	
	@Test
	public void shouldHandleUserAlreadyExistError() {
		User user = new User();
		when(result.hasErrors()).thenReturn(false);
		when(userDetailsService.findByEmail(null)).thenReturn(user);
		String actual = registrationController.processRegistration(user, result, model, request);
		verify(model, atMostOnce()).addAttribute("user",user);
		assertEquals("redirect:registration?error=UAE", actual);		
	}
	
	@Test
	public void shouldRegistrateNewUser() {
		User user = new User();
		when(result.hasErrors()).thenReturn(false);
		when(userDetailsService.findByEmail(null)).thenReturn(null);
		String actual = registrationController.processRegistration(user, result, model, request);
		verify(userDetailsService, atMostOnce()).save(user);
		verify(securityService, atMostOnce()).autoLogin(request, user.getEmail(), user.getPassword());
		assertEquals("redirect:/", actual);
	}
	

}
