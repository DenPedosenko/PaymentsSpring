package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.UserAccount;

public interface AccountRepository extends JpaRepository<UserAccount, Integer>{
	
}
