package com.packsure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipmentDTO {
	
	
	@JsonProperty("courier")
	private String courrier ;
	
	
	@JsonProperty("product_quantity")
	private int  product_quantity;

	public String getCourrier() {
		return courrier;
	}

	public void setCourrier(String courrier) {
		this.courrier = courrier;
	}

	@Override
	public String toString() {
		return "ShipmentDTO [courrier=" + courrier + "]";
	}

	public int  getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}
		
}
