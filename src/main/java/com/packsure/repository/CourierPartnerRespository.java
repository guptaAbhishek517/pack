package com.packsure.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.packsure.entity.CourierPartners;

public interface CourierPartnerRespository extends JpaRepository<CourierPartners, String> {

	

	

	Optional<CourierPartners> findByCourierId(String baseCourierId);


}
