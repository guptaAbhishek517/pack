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
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	private String channel_sku;
	
	
	private Double mrp;
	
	
	private Double cost;
	
	
	private Double netTotal;
	
	
	private String description;
	
	
	private Double discount;
	
	private String channelOrderProductId;

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
	
	
	public String getChannel_sku() {
		return channel_sku;
	}

	public void setChannel_sku(String channel_sku) {
		this.channel_sku = channel_sku;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(Double netTotal) {
		this.netTotal = netTotal;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getChannelOrderProductId() {
		return channelOrderProductId;
	}

	public void setChannelOrderProductId(String channelOrderProductId) {
		this.channelOrderProductId = channelOrderProductId;
	}

	

	public OrderItem(Long id, Order order, Long itemId, String itemName, Integer quantity, Double pricePerUnit,
			Double totalPrice, String channel_sku, Double mrp, Double cost, Double netTotal, String description,
			Double discount, String channelOrderProductId) {
		super();
		this.id = id;
		this.order = order;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
		this.channel_sku = channel_sku;
		this.mrp = mrp;
		this.cost = cost;
		this.netTotal = netTotal;
		this.description = description;
		this.discount = discount;
		this.channelOrderProductId = channelOrderProductId;
	}

	public OrderItem() {
		super();
		
	}

	// Getters, Setters

}
