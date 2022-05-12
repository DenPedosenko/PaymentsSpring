package com.epam.payments.model.statuses;

public enum UserRequestStatuses {
	ACTIVE(1), CLOSED(2);
	
	private int id;
	
	private UserRequestStatuses(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
