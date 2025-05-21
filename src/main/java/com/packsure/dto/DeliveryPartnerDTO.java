package com.packsure.dto;

public class DeliveryPartnerDTO {
	
	private String courierId;
	
	private String name;

	public String getCourierId() {
		return courierId;
	}

	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeliveryPartnerDTO(String courierId, String name) {
		super();
		this.courierId = courierId;
		this.name = name;
	}
	
	
	
	
}
