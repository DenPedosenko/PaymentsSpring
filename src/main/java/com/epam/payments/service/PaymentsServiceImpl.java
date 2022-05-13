package com.epam.payments.service;

import java.time.LocalDateTime;
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
import com.epam.payments.model.PaymentType;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.model.statuses.PaymentsStatuses;
import com.epam.payments.repository.PaymentRepository;
import com.epam.payments.repository.PaymentStatusRepository;
import com.epam.payments.repository.PaymentTypeRepository;

@Service
public class PaymentsServiceImpl implements PaymentsService {

	private PaymentRepository paymentRepository;
	private PaymentStatusRepository paymentStatusRepository;

	@Autowired
	public PaymentsServiceImpl(PaymentRepository paymentRepository, PaymentStatusRepository paymentStatusRepository,
			PaymentTypeRepository paymentTypeRepository) {
		this.paymentRepository = paymentRepository;
		this.paymentStatusRepository = paymentStatusRepository;
	}

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
				.stream().sorted(Comparator.comparing(Payment::getCreationDate).reversed()).collect(Collectors.toList());
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

	@Override
	public String proccessPayment(User user, UserAccount account, PaymentType type, double amount) {
		if (account.getBalance() - amount >= 0) {
			account.setBalance(account.getBalance() - amount);
			Payment payment = new Payment();
			payment.setCreationDate(LocalDateTime.now());
			payment.setPaymentStatus(
					paymentStatusRepository.findById(PaymentsStatuses.SENT.getId()).orElse(new PaymentStatus()));
			payment.setPaymentType(type);
			payment.setUser(user);
			payment.setUserAccount(account);
			payment.setAmount(amount);
			paymentRepository.save(payment);
			return "?operationStatus=success";
		} else {
			return "?operationStatus=error";
		}
	}

	@Override
	public String savePayment(User user, UserAccount account, PaymentType type, double amount) {
		Payment payment = new Payment();
		payment.setCreationDate(LocalDateTime.now());
		payment.setPaymentStatus(
				paymentStatusRepository.findById(PaymentsStatuses.PREPEARED.getId()).orElse(new PaymentStatus()));
		payment.setPaymentType(type);
		payment.setUser(user);
		payment.setUserAccount(account);
		payment.setAmount(amount);
		paymentRepository.save(payment);
		return "?operationStatus=save";
	}
}
