package com.epam.payments.model.localized;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.epam.payments.model.AccountStatus;

import lombok.Data;

@Data
@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LocalizedAccountStatuses {
	@EmbeddedId
    private LocalizedId localizedId;
     
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private AccountStatus status;
    
	private String name;
}
