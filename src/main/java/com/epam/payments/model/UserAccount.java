package com.epam.payments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="accounts")
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name_en")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@Column(name="balance")
	private double balance;
	
	@ManyToOne
	@JoinColumn(name="account_status_id")
	private AccountStatus accountStatus;
	
	@OneToOne(mappedBy = "account")
	private Card card;

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", name=" + name + ", balance=" + balance + ", accountStatus=" + accountStatus
				+ ", card=" + card + "]";
	}

}
