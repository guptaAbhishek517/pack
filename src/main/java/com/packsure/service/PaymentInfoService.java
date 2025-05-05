package com.packsure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.packsure.exception.DatabaseException;
import com.packsure.entity.Order;
import com.packsure.entity.PaymentInfo;
import com.packsure.exception.DuplicatePaymentException;
import com.packsure.repository.OrderRepository;
import com.packsure.repository.PaymentInfoRepository;
import com.packsure.exception.NoDataFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentInfoService {
	
	
	@Autowired
	private PaymentInfoRepository paymentInfoRepo;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<PaymentInfo> processAndReport(List<PaymentInfo> paymentInfos){
		
		  List<PaymentInfo> validPayments = new ArrayList<>();

		    for (PaymentInfo payment : paymentInfos) {

		        String orderNumber = String.valueOf(payment.getOrderID()).trim();

		       
		        if (orderNumber == null || orderNumber.isEmpty() || orderNumber.equalsIgnoreCase("null")) {
		            continue;
		        }
		        
		        System.out.println("Checking order: " + orderNumber);
		       
		        List<Order> matchingOrders = orderRepository.findAllByBarcodeNumber(orderNumber);
		       

		        if (!matchingOrders.isEmpty()) {
		        	boolean exists = paymentInfoRepo.existsByOrderIdAndPaymentDate(
		        			payment.getOrderID(), payment.getPaymentDate());
		        	
		        	if(!exists) {
		        	PaymentInfo saved = paymentInfoRepo.save(payment);
		            validPayments.add(saved);}
		        	else{
		        		throw new DuplicatePaymentException("payment Already exists for the Order ID " + payment.getOrderID()
		        		+ " on " + payment.getPaymentDate());
		        	}
		        }
		        
		        
		    }

		    return validPayments;
	}

	public List<PaymentInfo> getReportByDateRange(LocalDate startDate, LocalDate endDate) {
		try {
		List<PaymentInfo> result = paymentInfoRepo.findAllByPaymentDateBetween(startDate, endDate);
		if(result.isEmpty()) {
			throw new NoDataFoundException("No payment records for the given date");
		}
		return result;
		}catch(DataAccessException dae) {
			throw new DatabaseException("Database access error while retrieving payment Reocrds");
		}catch(NoDataFoundException ndfe){
			throw ndfe;
		}catch(Exception e) {
			throw new RuntimeException("An unexpected error occour while fetching payments record");
		}
		
		
	}

	public List<PaymentInfo> getReportByPaymentType(String paymentType) {
		try {
	        return paymentInfoRepo.findAllByPaymentType(paymentType);
	    } catch (Exception e) {
	        throw new RuntimeException("Error fetching payments by type", e);
	    }
	}

}
