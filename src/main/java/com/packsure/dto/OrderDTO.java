package com.packsure.dto;

import java.time.LocalDateTime;
import java.util.List;


public class OrderDTO {

	private Long orderId;
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String customerAddress;
	private LocalDateTime orderDate;
	private String status;
	private List<OrderItemDTO> items;
	
	private LocalDateTime packedAt;
	
	private LocalDateTime dispatchedAt;
	
	private String barcodeNumber;
	
	private String OrderType;
	
	private String paymentType;
	
	private String orderSource;
	
	private String deliverySource;
	
	private String orderStatus;
	
	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public LocalDateTime getPackedAt() {
		return packedAt;
	}

	public void setPackedAt(LocalDateTime packedAt) {
		this.packedAt = packedAt;
	}

	public LocalDateTime getDispatchedAt() {
		return dispatchedAt;
	}

	public void setDispatchedAt(LocalDateTime dispatchedAt) {
		this.dispatchedAt = dispatchedAt;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public String getOrderType() {
		return OrderType;
	}

	public void setOrderType(String orderType) {
		OrderType = orderType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getDeliverySource() {
		return deliverySource;
	}

	public void setDeliverySource(String deliverySource) {
		this.deliverySource = deliverySource;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
}
