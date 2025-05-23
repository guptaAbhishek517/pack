package com.packsure.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



public class OrderDTO {
	
	private String id; 
	
	private String channel_order_id;
	
	private String customer_name;
	
	private String customer_email;
	
	private String customer_phone;
	
	private String customer_address;
	
	private String customer_address2;
	
	private String channel_name;
	
	private String customer_city;
	
	private String customer_state;
	
	private String customer_pincode;
	
	private  String customer_country;
	
	private String payment_status;
	
	private Double total;
	
	private Double tax;
	
	private String rto_risk;
	
	private String status;
	
	private String payment_method;
	
	private String orderStatus;
	
	private String deliverySource;
	
	private String sukStatus;
	
	
	@JsonFormat(pattern = "dd MMM yyyy, hh:mm a", locale = "en", timezone = "Asia/Kolkata")
	private LocalDateTime channel_created_at;
	
	@JsonFormat(pattern = "dd MMM yyyy, hh:mm a", locale = "en", timezone = "Asia/Kolkata")
	private LocalDateTime created_at;
	
	@JsonFormat(pattern = "dd MMM yyyy, hh:mm a", locale = "en", timezone = "Asia/Kolkata")
	private LocalDateTime updated_at;
	
	private List<ShipmentDTO> shipments;
	
	private OthersDTO others;
	
	private LocalDateTime packedAt;
	
	private LocalDateTime dispactchedAt;
	
	private List<OrderItemDTO> products;

	public String getChannel_order_id() {
		return channel_order_id;
	}

	public void setChannel_order_id(String channel_order_id) {
		this.channel_order_id = channel_order_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public String getCustomer_phone() {
		return customer_phone;
	}

	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_address2() {
		return customer_address2;
	}

	public void setCustomer_address2(String customer_address2) {
		this.customer_address2 = customer_address2;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getCustomer_city() {
		return customer_city;
	}

	public void setCustomer_city(String customer_city) {
		this.customer_city = customer_city;
	}

	public String getCustomer_state() {
		return customer_state;
	}

	public void setCustomer_state(String customer_state) {
		this.customer_state = customer_state;
	}

	public String getCustomer_pincode() {
		return customer_pincode;
	}

	public void setCustomer_pincode(String customer_pincode) {
		this.customer_pincode = customer_pincode;
	}

	public String getCustomer_country() {
		return customer_country;
	}

	public void setCustomer_country(String customer_country) {
		this.customer_country = customer_country;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getRto_risk() {
		return rto_risk;
	}

	public void setRto_risk(String rto_risk) {
		this.rto_risk = rto_risk;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public LocalDateTime getChannel_created_at() {
		return channel_created_at;
	}

	public void setChannel_created_at(LocalDateTime channel_created_at) {
		this.channel_created_at = channel_created_at;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public List<ShipmentDTO> getShipments() {
		return shipments;
	}

	public void setShipments(List<ShipmentDTO> shipments) {
		this.shipments = shipments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OthersDTO getOthers() {
		return others;
	}

	public void setOthers(OthersDTO others) {
		this.others = others;
	}

	public List<OrderItemDTO> getProducts() {
		return products;
	}

	public void setProducts(List<OrderItemDTO> products) {
		this.products = products;
	}

	public LocalDateTime getPackedAt() {
		return packedAt;
	}

	public void setPackedAt(LocalDateTime packedAt) {
		this.packedAt = packedAt;
	}

	public LocalDateTime getDispactchedAt() {
		return dispactchedAt;
	}

	public void setDispactchedAt(LocalDateTime dispactchedAt) {
		this.dispactchedAt = dispactchedAt;
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

	public String getSukStatus() {
		return sukStatus;
	}

	public void setSukStatus(String sukStatus) {
		this.sukStatus = sukStatus;
	}


}	
