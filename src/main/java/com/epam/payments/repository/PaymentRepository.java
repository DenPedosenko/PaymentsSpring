package com.epam.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.User;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	public List<Payment> findAllByUser(User user);
	public List<Payment> findAllByUserAndPaymentStatus(User user, PaymentStatus status);
}
