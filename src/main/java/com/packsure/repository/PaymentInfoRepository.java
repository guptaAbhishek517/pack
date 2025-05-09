package com.packsure.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.packsure.entity.PaymentInfo;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

	boolean existsByOrderIdAndPaymentDate(String orderId, LocalDate paymentDate);

	List<PaymentInfo> findAllByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

	List<PaymentInfo> findByPaymentDateBetweenAndPaymentType(LocalDate startDate, LocalDate endDate,
			String paymentType);

}
