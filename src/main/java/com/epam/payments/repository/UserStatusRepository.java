package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.UserStatus;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {

}
