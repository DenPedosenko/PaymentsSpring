package com.epam.payments.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.payments.model.User;
import com.epam.payments.model.statuses.UserStatuses;
import com.epam.payments.repository.UserStatusRepository;
import com.epam.payments.repository.UserTypeRepository;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {

	
	private BCryptPasswordEncoder passwordEncoder;
	private  UserService userService;
	private UserTypeRepository userTypeRepository;
	private UserStatusRepository userStatusRepository;
	
	private final int USER_TYPE_USER = 1;

	@Autowired
	public UserDetailsServiceImpl(BCryptPasswordEncoder passwordEncoder, UserService userService,
			UserTypeRepository userTypeRepository, UserStatusRepository userStatusRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.userTypeRepository = userTypeRepository;
		this.userStatusRepository = userStatusRepository;
	}

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
		newUser.setUserStatus(userStatusRepository.getById(UserStatuses.ACTIVE.getId()));
		newUser.setUserType(userTypeRepository.getById(USER_TYPE_USER));
		userService.saveUser(newUser);
	}
}
