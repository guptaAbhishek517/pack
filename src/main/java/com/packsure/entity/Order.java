package com.packsure.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "OrderDetails")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(nullable = false, unique = true)
	private String barcodeNumber;
	
	
	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
	@JsonManagedReference
	private PaymentInfo paymentInfo;

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

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public Order(Long id, String customerName, String customerEmail, String customerPhone, String customerAddress,
			LocalDateTime orderDate, String status, List<OrderItem> items,BarcodePool barcodepool,
			LocalDateTime packedAt, LocalDateTime dispatchedAt, String barcodeNumber,
			String OrderType, PaymentInfo paymentInfo) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		this.orderDate = orderDate;
		this.status = status;
		this.items = items;
		this.barcodepool = barcodepool;
		this.dispatchedAt = dispatchedAt;
		this.packedAt = packedAt;
		this.barcodeNumber = barcodeNumber;
		this.OrderType = OrderType;
		this.paymentInfo = paymentInfo;	
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

}
