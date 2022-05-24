package com.epam.payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.epam.payments.model.User;
import com.epam.payments.model.UserStatus;
import com.epam.payments.model.UserType;
import com.epam.payments.repository.UserStatusRepository;
import com.epam.payments.repository.UserTypeRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	@Mock
	private UserService userService;
	@Mock
	private UserTypeRepository userTypeRepository;
	@Mock
	private UserStatusRepository userStatusRepository;
	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;
	
	@Test
	public void shouldLoadUserByName() {
		User user = new User();
		user.setEmail("email");
		user.setPassword("password");
		UserType type = new UserType();
		type.setName("name");
		user.setUserType(type);
		when(userService.findByEmail("email")).thenReturn(user);
		UserDetails expected = org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
				.password(user.getPassword()).roles(user.getUserType().getName()).build();
		UserDetails actual = userDetailsService.loadUserByUsername("email");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldThrowException() {
		when(userService.findByEmail("email")).thenReturn(null);
		assertThrows( UsernameNotFoundException.class, ()->userDetailsService.loadUserByUsername("email"));
	}
	
	@Test
	public void shoulSaveUser() {
		User user = new User();
		user.setPassword("11");
		UserType type = new UserType();
		UserStatus status = new UserStatus();
		when(passwordEncoder.encode("11")).thenReturn("22");
		when(userStatusRepository.getById(anyInt())).thenReturn(status);
		when(userTypeRepository.getById(anyInt())).thenReturn(type);
		User newUser = new User();
		newUser.setPassword("22");
		newUser.setUserStatus(status);
		newUser.setUserType(type);
		
		userDetailsService.save(user);
		
		verify(userService, atLeastOnce()).saveUser(newUser);	
	}

}
