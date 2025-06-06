package com.packsure.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PaymentInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String orderId;
	
	private String paymentType;
	
	private double dueAmount;
	
	private double receivedAmount;
	
	private LocalDate paymentDate;

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getOrderID() {
		return orderId;
	}

	public void setOrderID(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentInfo(String orderId, String paymentType, double dueAmount, double receivedAmount, LocalDate paymentDate) {
		super();
		this.orderId = orderId;
		this.paymentType = paymentType;
		this.dueAmount = dueAmount;
		this.receivedAmount = receivedAmount;
		this.paymentDate = paymentDate;
		
	}

	public PaymentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
