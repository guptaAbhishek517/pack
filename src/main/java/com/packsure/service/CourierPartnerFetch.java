package com.packsure.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;


import com.packsure.dto.CourierApiResponse;
import com.packsure.dto.CourierPartnersDTO;
import com.packsure.entity.CourierPartners;
import com.packsure.repository.CourierPartnerRespository;

@Service
public class CourierPartnerFetch {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CourierPartnerRespository courierPartnerRepository;

    public void fetechAndSavePartners() {

    	    String token = tokenService.getToken();
    	    String apiUrl = "https://apiv2.shiprocket.in/v1/external/courier/courierListWithCounts";

    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setBearerAuth(token);
    	    HttpEntity<String> entity = new HttpEntity<>(headers);

    	    ResponseEntity<CourierApiResponse> response = new RestTemplate()
    	            .exchange(apiUrl, HttpMethod.GET, entity, CourierApiResponse.class);
    	    
    	    System.out.println("🛰 API call made, status code: " + response.getStatusCode());

    	    CourierApiResponse courierApiResponse = response.getBody();

    	    if (courierApiResponse != null && response.getStatusCode().is2xxSuccessful()) {
    	        List<CourierPartnersDTO> partners = courierApiResponse.getCourier_data();

    	        if (partners != null) {
    	            System.out.println("✅ Total couriers fetched: " + partners.size());

    	            int saveCount = 0;

    	            for (CourierPartnersDTO dto : partners) {
    	                Optional<CourierPartners> existing = courierPartnerRepository
    	                        .findByCourierId(dto.getBase_courier_id());

    	                if (existing.isEmpty()) {
    	                    CourierPartners courier = new CourierPartners();
    	                    courier.setCourierId(dto.getId());
    	                    courier.setBaseCourierId(dto.getBase_courier_id());
    	                    courier.setName(dto.getName());
    	                    courier.setMasterCompany(dto.getMaster_compnay());

    	                    courierPartnerRepository.save(courier);
    	                    saveCount++;
    	                }
    	            }

    	            System.out.println("✅ New couriers saved to DB: " + saveCount);

    	        } else {
    	            System.out.println("❌ courier_data is null");
    	        }
    	    } else {
    	        System.out.println("❌ Failed to fetch courier partners. Status: " + response.getStatusCode());
    	    }
    	    
    }
}
