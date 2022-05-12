package com.epam.payments.service;

import java.util.List;
import java.util.Map;

import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.User;

public interface PaymentsService {
	public Map<String, List<Payment>> getUserPaymentsCollectedByDate(User user);
	
	public Map<String, List<Payment>> getUserPaymentsByStatus(User user, PaymentStatus paymentStatus);
}
