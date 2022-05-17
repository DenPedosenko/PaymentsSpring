package com.epam.payments.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.epam.payments.model.localized.LocalizedAccountStatuses;

import lombok.Data;

@Data
@Entity
@Table(name = "account_statuses")
public class AccountStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy = "status", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
	@MapKey(name = "localizedId.locale")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Map<String, LocalizedAccountStatuses> localizations = new HashMap<>();

	public String getName(String locale) {
		return localizations.get(locale).getName();
	}	
}
