package com.packsure.dto;

public class OrderStatusUpdateDto {
	
	public String channel_order_id;
	public String orderStatus;
	public String deliverySource;
	private String reason;

	public String getChannel_order_id() {
		return channel_order_id;
	}
	public void setChannel_order_id(String channel_order_id) {
		this.channel_order_id = channel_order_id;
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
