package com.epam.payments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.User;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	public List<Payment> findAllByUser(User user);
	public Page<Payment> findAllByUser(User user, Pageable pageable);
	public List<Payment> findAllByUserAndPaymentStatus(User user, PaymentStatus status);
}
