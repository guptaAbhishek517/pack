package com.packsure.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
public class OrderItemDTO {
	
	
	@JsonProperty("product_id")
	private Long itemId;
	
	
	@JsonProperty("name")
	private String itemName;
	
	@JsonProperty("quantity")
	private Integer quantity;
	
	@JsonProperty("price")
	private Double pricePerUnit;
	
	@JsonProperty("selling_price")
	private Double totalPrice;
	
	@JsonProperty("channel_sku")
	private String channel_sku;
	
	
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
	@Override
	public String toString() {
		return "OrderItemDTO [itemId=" + itemId + ", itemName=" + itemName + ", quantity=" + quantity
				+ ", pricePerUnit=" + pricePerUnit + ", totalPrice=" + totalPrice + ", channel_sku=" + 
				 "]";
	}
	
	
}
