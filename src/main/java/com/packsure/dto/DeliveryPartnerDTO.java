package com.packsure.dto;

public class DeliveryPartnerDTO {
	
	private Long courierId;
	
	private String name;

	public Long getCourierId() {
		return courierId;
	}

	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeliveryPartnerDTO(Long courierId, String name) {
		super();
		this.courierId = courierId;
		this.name = name;
	}
	
	
	
	
}
