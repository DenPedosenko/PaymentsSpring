package com.epam.payments.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.epam.payments.model.User;

public interface CustomUserDetailsService extends UserDetailsService {

	User findByEmail(String email);

	void save(User user);

}
