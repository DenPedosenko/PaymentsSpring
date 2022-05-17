package com.epam.payments.model.localized;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.epam.payments.model.PaymentType;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedPaymentTypes {
	@EmbeddedId
    private LocalizedId localizedId;
     
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private PaymentType type;
     
    @Override
	public int hashCode() {
		return Objects.hash(localizedId, name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalizedPaymentTypes other = (LocalizedPaymentTypes) obj;
		return Objects.equals(localizedId, other.localizedId) && Objects.equals(name, other.name)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "LocalizedPaymentTypes [localizedId=" + localizedId + ", type=" + type + ", name=" + name + "]";
	}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}


	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
