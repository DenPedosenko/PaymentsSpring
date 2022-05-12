package com.epam.payments.model.statuses;

public enum UserAccountStatuses {
	ACTIVE(1), BLOCKED(2);

	private int id;

	private UserAccountStatuses(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
