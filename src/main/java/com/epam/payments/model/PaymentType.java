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

import com.epam.payments.model.localized.LocalizedPaymentTypes;

import lombok.Data;

@Data
@Entity
@Table(name="payment_types")
public class PaymentType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "type", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
	@MapKey(name = "localizedId.locale")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Map<String, LocalizedPaymentTypes> localizations = new HashMap<>();

	public String getName(String locale) {
		return localizations.get(locale).getName();
	}
	
}
