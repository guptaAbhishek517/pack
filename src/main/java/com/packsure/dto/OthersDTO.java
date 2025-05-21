package com.packsure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OthersDTO {
	
	@JsonProperty("confirmed")
	private Boolean confirmed;

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	
	
}
