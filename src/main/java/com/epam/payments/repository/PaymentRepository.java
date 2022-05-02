package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
