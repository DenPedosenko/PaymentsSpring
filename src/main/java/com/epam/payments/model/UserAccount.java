package com.epam.payments.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.context.i18n.LocaleContextHolder;

import com.epam.payments.model.localized.LocalizedUserAccounts;

import lombok.Data;

@Data
@Entity
@Table(name="accounts")
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy = "account", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
	@MapKey(name = "localizedId.locale")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Map<String, LocalizedUserAccounts> localizations = new HashMap<>();
	
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

	public String getName(String locale) {
		return localizations.get(locale).getName();
	}
	
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", name=" + getName(LocaleContextHolder.getLocale().toString()) + ", balance=" + balance + ", accountStatus=" + accountStatus
				+ ", card=" + card + "]";
	}

}
