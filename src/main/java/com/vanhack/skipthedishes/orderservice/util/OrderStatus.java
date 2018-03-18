package com.vanhack.skipthedishes.orderservice.util;

/**
 * Enum with the status os the orders
 * @author clebersimm
 *
 */
public enum OrderStatus {

	PENDENT("Pendent"),
	IN_PROGRESS("In progress"),
	READY_PICK_UP("Ready to pick up"),
	DELIVERED("Delivered"),
	CANCELLED("Cancelled");
	
	private String status;
	
	private OrderStatus(String status) {
		this.status = status;
	}
	
	public String status() {
		return status;
	}
	
}
