package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.AccountStatus;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer>{

}
