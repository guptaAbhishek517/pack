package com.packsure.controller;

import com.packsure.entity.PaymentInfo;
import com.packsure.exception.DuplicatePaymentException;
import com.packsure.service.PaymentInfoService;
import com.packsure.utility.CSVPayementInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/payment-info")
public class CSVController {

	@Autowired
	private PaymentInfoService paymentInfoService;

	@PostMapping("/upload-csv")
	public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file) {
	    String fileType = file.getContentType();

	    if (!"text/csv".equals(fileType) && !file.getOriginalFilename().endsWith(".csv")) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("message", "Please upload a valid CSV file.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }

	    try {
	        List<PaymentInfo> paymentInfos = CSVPayementInfo.csvPayInfo(file.getInputStream());
	        List<PaymentInfo> savedInfos = paymentInfoService.processAndReport(paymentInfos);

	        Map<String, Object> successResponse = new HashMap<>();
	        successResponse.put("success", true);
	        successResponse.put("message", savedInfos.size() + " records saved successfully.");
	        successResponse.put("data", savedInfos);

	        return ResponseEntity.ok(successResponse);
	    } catch (DuplicatePaymentException dpe) {
	        Map<String, Object> conflictResponse = new HashMap<>();
	        conflictResponse.put("success", false);
	        conflictResponse.put("message", dpe.getMessage());
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("message", "Failed to process CSV file: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}


	@GetMapping("/by-date")
	public ResponseEntity<List<PaymentInfo>> getReportByDate(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		System.out.println("Start Date: " + startDate);
		System.out.println("End Date: " + endDate);
		
		try {
		List<PaymentInfo> report = paymentInfoService.getReportByDateRange(startDate, endDate);
		if(report.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(report);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
		
	}
	
    @GetMapping("/byPaymentType")
	public ResponseEntity<List<PaymentInfo>> getReportByPaymentType(@RequestParam String type){
		try {
			String paymentType = String.valueOf(type.toLowerCase());
			List<PaymentInfo> report = paymentInfoService.getReportByPaymentType(paymentType);
			return ResponseEntity.ok(report);
		}catch(IllegalArgumentException e){
			return ResponseEntity.badRequest().body(Collections.emptyList());
		}
 
	}
}
