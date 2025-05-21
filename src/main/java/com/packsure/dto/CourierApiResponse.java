package com.packsure.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourierApiResponse {
	
	
	@JsonProperty("courier_data")
	private List<CourierPartnersDTO> courier_data;

	public List<CourierPartnersDTO> getCourier_data() {
		return courier_data;
	}

	public void setCourier_data(List<CourierPartnersDTO> courier_data) {
		this.courier_data = courier_data;
	}
	
}
