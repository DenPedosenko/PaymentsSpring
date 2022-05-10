package com.epam.payments.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.payments.model.User;
import com.epam.payments.model.UserStatus;
import com.epam.payments.model.UserType;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private  UserService userService;


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(@Valid String username) throws UsernameNotFoundException {
		User user = userService.findByEmail(username);
		if (user == null)
			throw new UsernameNotFoundException(username);

		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
				.password(user.getPassword()).roles(user.getUserType().getName()).build();
	}

	@Override
	@Transactional
	public User findByEmail(String email) {
		return userService.findByEmail(email);
	}

	@Override
	@Transactional
	public void save(User user) {
		User newUser = new User();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setUserStatus(entityManager.getReference(UserStatus.class, 1));
		newUser.setUserType(entityManager.getReference(UserType.class, 1));
		userService.saveUser(newUser);
	}
}
