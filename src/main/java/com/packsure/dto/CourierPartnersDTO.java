package com.packsure.dto;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourierPartnersDTO {
	
	@JsonProperty("name")
	private String name;

	@Id
	@JsonProperty("id")
	private String courierId;
	
	@JsonProperty("base_courier_id")
	private String baseCourierId;
	
	@JsonProperty("master_company")
	private String masterCompany;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourierId() {
		return courierId;
	}

	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}

	public String getBaseCourierId() {
		return baseCourierId;
	}

	public void setBaseCourierId(String baseCourierId) {
		this.baseCourierId = baseCourierId;
	}

	public String getMasterCompany() {
		return masterCompany;
	}

	public void setMasterCompany(String masterCompany) {
		this.masterCompany = masterCompany;
	}
	
	

}
