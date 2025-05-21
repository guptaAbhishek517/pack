package com.packsure.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "courier_partners")
public class CourierPartners {

	private String name;

	@Id
	private String courierId;
	
	private String baseCourierId;
	
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

	public CourierPartners(String name, String courierId, String baseCourierId, String masterCompany) {
		super();
		this.name = name;
		this.courierId = courierId;
		this.baseCourierId = baseCourierId;
		this.masterCompany = masterCompany;
	}

	public CourierPartners() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
