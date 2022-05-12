package com.epam.payments.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.payments.model.Payment;
import com.epam.payments.model.PaymentStatus;
import com.epam.payments.model.User;
import com.epam.payments.repository.PaymentRepository;

@Service
public class PaymentsServiceImpl implements PaymentsService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public Map<String, List<Payment>> getUserPaymentsCollectedByDate(User user) {
		Map<String, List<Payment>> payments = new HashMap<>();
		for (Payment payment : paymentRepository.findAllByUser(user)) {
			putPayment(payments, payment);
		}
		return payments;
	}

	@Override
	public Map<String, List<Payment>> getUserPaymentsByStatus(User user, PaymentStatus paymentStatus) {
		Map<String, List<Payment>> payments = new LinkedHashMap<String, List<Payment>>();
		List<Payment> orderedByDatePayments = paymentRepository.findAllByUserAndPaymentStatus(user, paymentStatus)
				.stream().sorted(Comparator.comparing(Payment::getCreationDate)).collect(Collectors.toList());
		for (Payment payment : orderedByDatePayments) {
			putPayment(payments, payment);
		}
		return payments;
	}

	private static void putPayment(Map<String, List<Payment>> payments, Payment payment) {
		String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(payment.getCreationDate());
		payments.put(date, getPaymentsByDate(payments, payment, date));
	}

	private static List<Payment> getPaymentsByDate(Map<String, List<Payment>> payments, Payment payment, String date) {
		List<Payment> paymentsByDate = payments.get(date);
		if (paymentsByDate == null) {
			paymentsByDate = List.of(payment);
		} else {
			List<Payment> tempList = new ArrayList<>(paymentsByDate);
			tempList.add(payment);
			paymentsByDate = tempList;
		}
		return paymentsByDate;
	}
}
