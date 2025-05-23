package com.packsure.dto;



public class OrderItemDTO {

	private Long id;
	
	private Long product_id;
	
	private String name;
	
	private Integer quantity;

//	private String  channel_order_product_id;
	
	private Double price;
	
	private String status;
	
	private String description;
	
	private String channel_sku;
	
	private Double discount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

//	public String getChannel_order_product_id() {
//		return channel_order_product_id;
//	}
//
//	public void setChannel_order_product_id(String channel_order_product_id) {
//		this.channel_order_product_id = channel_order_product_id;
//	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannel_sku() {
		return channel_sku;
	}

	public void setChannel_sku(String channel_sku) {
		this.channel_sku = channel_sku;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
	
	
   

}
