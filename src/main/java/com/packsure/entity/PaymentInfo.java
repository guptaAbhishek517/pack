package com.packsure.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class PaymentInfo {
	
	
	private Long orderID;
	
	private String paymentType;
	
	private double dueAmount;
	
	private double receivedAmount;
	
	@Temporal(TemporalType.DATE)
	private Date paymentDate;

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

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentInfo(Long orderID, String paymentType, double dueAmount, double receivedAmount, Date paymentDate) {
		super();
		this.orderID = orderID;
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
