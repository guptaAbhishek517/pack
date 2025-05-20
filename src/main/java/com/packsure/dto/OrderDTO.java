package com.packsure.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


public class OrderDTO {
	
	
	private String orderId;
	
	@JsonProperty("customer_name")
	private String customerName;
	
	@JsonProperty("customer_email")
	private String customerEmail;
	
	@JsonProperty("customer_phone")
	private String customerPhone;
	
	
	@JsonProperty("customer_address")
	private String customerAddress;
	
	@JsonProperty("channel_created_at")
	@JsonFormat(pattern = "dd MMM yyyy, hh:mm a", locale = "en", timezone = "Asia/Kolkata")
	private LocalDateTime orderDate;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("products")
	private List<OrderItemDTO> items;
	
	private LocalDateTime packedAt;
	
	private LocalDateTime dispatchedAt;
	
	@JsonProperty("channel_order_id")
	private String barcodeNumber;
	
	private String OrderType;
	
	@JsonProperty("payment_method")
	private String paymentType;
	
	
	@JsonProperty("channel_name")
	private String orderSource;
	
	
	private String deliverySource;
	
	
	private String orderStatus;
	
	
	@JsonProperty("customer_city")
    private String city;
	
	@JsonProperty("customer_state")
	private String state;
	
	@JsonProperty("customer_address_2")
	private String Address2;
	
	
	@JsonProperty("customer_country")
	private String Country;
	
	@JsonProperty("customer_pincode")
	private String zipCode;
	
	@JsonProperty("payment_status")
	private String paymentStatus;
	
	
	@JsonProperty("rto_risk")
	private String rto_risk;
	
	
	@JsonProperty("shipments")
	private List<ShipmentDTO> shipments;
	
	
	
	public List<ShipmentDTO> getShipments() {
		return shipments;
	}

	public void setShipments(List<ShipmentDTO> shipments) {
		this.shipments = shipments;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
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
	
	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress2() {
		return Address2;
	}

	public void setAddress2(String address2) {
		Address2 = address2;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	

	public String getRto_risk() {
		return rto_risk;
	}

	public void setRto_risk(String rto_risk) {
		this.rto_risk = rto_risk;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", customerName=" + customerName + ", customerEmail=" + customerEmail
				+ ", customerPhone=" + customerPhone + ", customerAddress=" + customerAddress + ", orderDate="
				+ orderDate + ", status=" + status + ", items=" + items + ", packedAt=" + packedAt + ", dispatchedAt="
				+ dispatchedAt + ", barcodeNumber=" + barcodeNumber + ", OrderType=" + OrderType + ", paymentType="
				+ paymentType + ", orderSource=" + orderSource + ", deliverySource=" + deliverySource + ", orderStatus="
				+ orderStatus + ", city=" + city + ", state=" + state + ", Address2=" + Address2 + ", Country="
				+ Country + ", zipCode=" + zipCode + ", paymentStatus=" + paymentStatus + ", rto_risk=" + rto_risk
				+ ", shipments=" + shipments + "]";
	}
	
	
	
	
	
	
}
