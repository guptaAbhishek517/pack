package com.packsure.dto;

public class OrderStatusUpdateDto {
	
	public String orderId;
	public String orderStatus;
	public String deliverySource;
	private String reason;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDeliverySource() {
		return deliverySource;
	}
	public void setDeliverySource(String deliverySource) {
		this.deliverySource = deliverySource;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
		
}
