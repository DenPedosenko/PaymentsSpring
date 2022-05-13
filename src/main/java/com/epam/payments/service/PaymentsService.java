package com.epam.payments.service;

import java.util.List;
import java.util.Map;

import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.PaymentType;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;

public interface PaymentsService {
	public Map<String, List<Payment>> getUserPaymentsCollectedByDate(User user);
	public Map<String, List<Payment>> getUserPaymentsByStatus(User user, PaymentStatus paymentStatus);
	public String proccessPayment(User user, UserAccount account, PaymentType type, double amount);
	public String savePayment(User user, UserAccount account, PaymentType type, double amount);
}
