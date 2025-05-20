package com.packsure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipmentDTO {
	
	
	@JsonProperty("courier")
	private String courrier ;

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
	
	
	
	
}
