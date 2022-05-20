package com.epam.payments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.payments.model.User;
import com.epam.payments.model.UserType;
import com.epam.payments.model.statuses.UserStatuses;
import com.epam.payments.repository.UserRepository;
import com.epam.payments.repository.UserStatusRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserStatusRepository userStatusRepository;

	@Autowired
	public UserService(UserRepository userRepository, UserStatusRepository userStatusRepository) {
		this.userRepository = userRepository;
		this.userStatusRepository = userStatusRepository;
	}

	public User getById(int id) {
		return userRepository.getById(id);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Page<User> findAllUsers(Pageable pageable) {
		UserType type = new UserType();
		type.setId(1);
		type.setName("User");
		return userRepository.findAllByUserType(type, pageable);
	}

	public void blockUser(int id) {
		User user = userRepository.getById(id);
		user.setUserStatus(userStatusRepository.getById(UserStatuses.BLOCKED.getId()));
		userRepository.save(user);
	}

	public void unlockUser(int id) {
		User user = userRepository.getById(id);
		user.setUserStatus(userStatusRepository.getById(UserStatuses.ACTIVE.getId()));
		userRepository.save(user);
	}
}
