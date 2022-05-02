package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String email);

}
