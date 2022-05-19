package com.epam.payments.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;


public interface AccountRepository extends JpaRepository<UserAccount, Integer>{
	public List<UserAccount> findAllByUser(User user, Sort sort);
}
