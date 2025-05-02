package com.packsure.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.packsure.entity.PaymentInfo;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long>{

	List<PaymentInfo> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

}
