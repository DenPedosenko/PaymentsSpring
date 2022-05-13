package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.RequestStatus;

public interface UserRequestStatusRepository extends JpaRepository<RequestStatus, Integer>{

}
