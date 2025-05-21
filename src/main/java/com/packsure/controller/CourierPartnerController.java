package com.packsure.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packsure.dto.DeliveryPartnerDTO;
import com.packsure.entity.CourierPartners;
import com.packsure.repository.CourierPartnerRespository;
import com.packsure.service.CourierPartnerFetch;

@RestController
@RequestMapping("/api")
public class CourierPartnerController {
	
	    @Autowired
	    private CourierPartnerFetch courierPartnerFetch;
	    
	    @Autowired
	    private CourierPartnerRespository  courierPartnerRespository;

	    @GetMapping("/test-fetch")
	    public String testFetch() {
	        courierPartnerFetch.fetechAndSavePartners();
	        return "Fetch triggered";
	    }
	    
	    @GetMapping("/courier-partners")
	    public List<DeliveryPartnerDTO> getCourierPartners() {
	        List<CourierPartners> partners = courierPartnerRespository.findAll();

	        
	        return partners.stream()
	                .map(p -> new DeliveryPartnerDTO(p.getCourierId(), p.getName()))
	                .collect(Collectors.toList());
	    }
}
