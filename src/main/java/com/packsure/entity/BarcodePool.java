package com.packsure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BarcodePool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String barcodeNumber;

    private boolean isUsed = false;
    
//    private String barcodeImagePath;
       
    @OneToOne
	@JoinColumn(name = "order_id", unique = true)
	private Order order; 
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	
//	public String getBarcodeImagePath() {
//		return barcodeImagePath;
//	}
//
//	public void setBarcodeImagePath(String barcodeImagePath) {
//		this.barcodeImagePath = barcodeImagePath;
//	}
	
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BarcodePool(Long id, String barcodeNumber, boolean isUsed, Order order) {
		super();
		this.id = id;
		this.barcodeNumber = barcodeNumber;
		this.isUsed = isUsed;
//		this.barcodeImagePath = barcodeImagePath;
		this.order = order;
		
	}

	public BarcodePool() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}