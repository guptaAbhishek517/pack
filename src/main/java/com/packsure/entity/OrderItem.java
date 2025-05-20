package com.packsure.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;

	private Long itemId;

	private String itemName;

	private Integer quantity;

	private Double pricePerUnit;

	private Double totalPrice;
	
	private Long channel_sku;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	

  
	public Long getChannel_sku() {
		return channel_sku;
	}

	public void setChannel_sku(Long channel_sku) {
		this.channel_sku = channel_sku;
	}

	public OrderItem(Long id, Order order, Long itemId, String itemName, Integer quantity, Double pricePerUnit,
			Double totalPrice, LocalDateTime packedAt, LocalDateTime dispatchedAt, String orderId, Long channel_sku) {
		super();
		this.id = id;
		this.order = order;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
		this.channel_sku = channel_sku;
		
		
	}

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters, Setters

}
