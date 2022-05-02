package com.epam.payments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.payments.model.User;
import com.epam.payments.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getById(int id) {
		return userRepository.getById(id);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User saveUser(User user) { 
		return userRepository.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
