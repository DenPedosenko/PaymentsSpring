package com.epam.payments.model.statuses;

public enum PaymentsStatuses {
	PREPEARED(1), SENT(2);
	
	private int id;
	
	private PaymentsStatuses(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
