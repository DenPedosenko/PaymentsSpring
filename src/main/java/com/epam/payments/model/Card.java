package com.epam.payments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.i18n.LocaleContextHolder;

import lombok.Data;

@Data
@Entity
@Table(name="cards")
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="card_number")
	private String cardNumber;
	@Column(name="exp_date")
	private String expDate;
	private String cvv;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id")
	private UserAccount account;

	@Override
	public String toString() {
		return account.getName(LocaleContextHolder.getLocale().toString()) + " Card ****" + cardNumber.substring(12)+"   Balance: "+account.getBalance();
	}

}
