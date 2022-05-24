package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

}
