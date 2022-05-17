package com.epam.payments.model.localized;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.epam.payments.model.PaymentStatus;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedPaymentStatuses {
	@EmbeddedId
    private LocalizedId localizedId;
     
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private PaymentStatus status;
     
    @Override
	public int hashCode() {
		return Objects.hash(localizedId, name, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalizedPaymentStatuses other = (LocalizedPaymentStatuses) obj;
		return Objects.equals(localizedId, other.localizedId) && Objects.equals(name, other.name)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "LocalizedPaymentStatus [localizedId=" + localizedId + ", status=" + status + ", name=" + name + "]";
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
