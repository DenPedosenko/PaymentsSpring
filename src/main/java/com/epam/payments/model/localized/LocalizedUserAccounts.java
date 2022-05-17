package com.epam.payments.model.localized;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.epam.payments.model.UserAccount;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="localized_accounts")
public class LocalizedUserAccounts {
	
	@EmbeddedId
    private LocalizedId localizedId;
     
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private UserAccount account;
   
    
    private String name;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	public LocalizedId getLocalizedId() {
		return localizedId;
	}

	public UserAccount getAccount() {
		return account;
	}

	public void setLocalizedId(LocalizedId localizedId) {
		this.localizedId = localizedId;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account, localizedId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalizedUserAccounts other = (LocalizedUserAccounts) obj;
		return Objects.equals(account, other.account) && Objects.equals(localizedId, other.localizedId);
	}

	@Override
	public String toString() {
		return "LocalizedUserAccounts [localizedId=" + localizedId + ", account=" + account + "]";
	}
 
}
