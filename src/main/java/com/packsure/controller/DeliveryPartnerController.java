package com.packsure.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeliveryPartnerController {
	
	@GetMapping("/delivery/Partners")
	public ResponseEntity<List<String>> getDeliveryPartner(){
		List<String> partners = Arrays.asList(
				    "Delhivery",
		            "Ecom Express",
		            "Blue Dart",
		            "Shadowfax",
		            "DTDC",
		            "Shiprocket",
		            "XpressBees",
		            "Ekart",
		            "India Post"
				);
		return ResponseEntity.ok(partners);
	}
}
