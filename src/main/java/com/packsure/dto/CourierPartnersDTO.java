package com.packsure.dto;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourierPartnersDTO {
	
	private Long id;
	
	
	private String base_courier_id;
	
	private String name;
	
	private String master_compnay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getBase_courier_id() {
		return base_courier_id;
	}

	public void setBase_courier_id(String base_courier_id) {
		this.base_courier_id = base_courier_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaster_compnay() {
		return master_compnay;
	}

	public void setMaster_compnay(String master_compnay) {
		this.master_compnay = master_compnay;
	}


	
	

}
