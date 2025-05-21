package com.packsure.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "OrderDetails")
public class Order {

	private Long id;

	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String customerAddress;
	private LocalDateTime orderDate;
	private String status;
	
	private LocalDateTime packedAt;
	
	private LocalDateTime dispatchedAt;

	private String OrderType;
	
	@OneToOne
	@JoinColumn(name = "barcode_pool_id")
	@JsonIgnore
	private BarcodePool barcodepool;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<OrderItem> items = new ArrayList<>();
	
	@Id
	@Column(nullable = false, unique = true)
	private String barcodeNumber;
	
	private String orderStatus;
	
	private String orderSource;
	
	private String deliverySource;
	
//	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private PaymentInfo paymentInfo;
	
	private String masterId;
	
	private String paymentType;
	
	private String city;
	
	private String state;
	
	private String Address2;
	
	private String Country;
	
	private String zipCode;
	
	private String paymentStatus;
	
	private String CancelReason;
	
	private String rto_risk;

	private Boolean confirmed;
	
	private int product_quantity;
	
	

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	public BarcodePool getBarcodepool() {
		return barcodepool;
	}

	public void setBarcodepool(BarcodePool barcodepool) {
		this.barcodepool = barcodepool;
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
	
	

	
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public String getDeliverySource() {
		return deliverySource;
	}

	public void setDeliverySource(String deliverySource) {
		this.deliverySource = deliverySource;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCancelReason() {
		return CancelReason;
	}

	public void setCancelReason(String cancelReason) {
		CancelReason = cancelReason;
	}


	public String getRto_risk() {
		return rto_risk;
	}

	public void setRto_risk(String rto_risk) {
		this.rto_risk = rto_risk;
	}
	
	
	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public int getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	public Order(Long id, String customerName, String customerEmail, String customerPhone, String customerAddress,
			LocalDateTime orderDate, String status, LocalDateTime packedAt, LocalDateTime dispatchedAt,
			String orderType, BarcodePool barcodepool, List<OrderItem> items, String barcodeNumber, String orderStatus,
			String orderSource, String deliverySource, String paymentType, String city, String state, String address2,
			String country, String zipCode, String paymentStatus, String cancelReason, String rto_risk,
			 Boolean confirmed, int product_quantity, String masterId) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.orderDate = orderDate;
		this.status = status;
		this.packedAt = packedAt;
		this.dispatchedAt = dispatchedAt;
		this.OrderType = orderType;
		this.barcodepool = barcodepool;
		this.items = items;
		this.barcodeNumber = barcodeNumber;
		this.orderStatus = orderStatus;
		this.orderSource = orderSource;
		this.deliverySource = deliverySource;
		this.paymentType = paymentType;
		this.city = city;
		this.state = state;
		this.Address2 = address2;
		this.Country = country;
		this.zipCode = zipCode;
		this.paymentStatus = paymentStatus;
		this.CancelReason = cancelReason;
		this.rto_risk = rto_risk;
		this.confirmed = confirmed;
		this.product_quantity = product_quantity;
		this.masterId = masterId;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

}
