package com.epam.payments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.User;
import com.epam.payments.model.UserType;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
	public Page<User> findAllByUserType(UserType type, Pageable pageable);

}
