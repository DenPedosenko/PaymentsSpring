package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {

}
