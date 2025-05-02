package com.packsure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packsure.entity.PaymentInfo;
import com.packsure.repository.OrderRepository;
import com.packsure.repository.PaymentInfoRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class PaymentInfoService {
	
	
	@Autowired
	private PaymentInfoRepository paymentInfoRepo;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Map<String,List<PaymentInfo>> processAndReport(List<PaymentInfo> paymentInfos){
		
		Map<String, List<PaymentInfo>> report = new HashMap<>();
		
		for(PaymentInfo payment : paymentInfos) {
		
			 String orderNumber = String.valueOf(payment.getOrderID());

			    if (orderRepository.findByBarcodeNumber(orderNumber) != null) {
			        String type = payment.getPaymentType();
			        report.computeIfAbsent(type, k -> new ArrayList<>()).add(payment);
			    }
		}
		return report;
	}

	public Map<String, List<PaymentInfo>> getReportByDateRange(LocalDate startDate, LocalDate endDate) {
		List<PaymentInfo> paymentsInRange = paymentInfoRepo.findByPaymentDateBetween(startDate, endDate);
		
		Map<String, List<PaymentInfo>> report = new HashMap<>();
		for(PaymentInfo payment : paymentsInRange) {
			String orderNumber = String.valueOf(payment.getOrderID());
			if(orderRepository.findByBarcodeNumber(orderNumber)!=null) {
				String type = payment.getPaymentType();
				report.computeIfAbsent(type, k-> new ArrayList<>()).add(payment);
			}
		}
		
		return report;
	}
}
