package com.epam.payments.model.statuses;

public enum UserStatuses {
	ACTIVE(1), BLOCKED(2);
	
	private int id;
	
	private UserStatuses(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
