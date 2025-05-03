package com.packsure.controller;

import com.packsure.entity.PaymentInfo;
import com.packsure.service.PaymentInfoService;
import com.packsure.utility.CSVPayementInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-info")
public class CSVController {
	
	
	@Autowired
	private PaymentInfoService paymentInfoService;
	
	@PostMapping("/upload-csv")
	public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file) {
	    String fileType = file.getContentType();

	    if (!fileType.equals("text/csv") && !file.getOriginalFilename().endsWith(".csv")) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Please upload a valid CSV file.");
	    }

	    try {
	        List<PaymentInfo> paymentInfos = CSVPayementInfo.csvPayInfo(file.getInputStream());

	        List<PaymentInfo> savedInfos = paymentInfoService.processAndReport(paymentInfos);

	        return ResponseEntity.ok(savedInfos);
	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to process CSV file: " + e.getMessage());
	    }
	}
//    
//    @GetMapping("/by-date")
//    public ResponseEntity<?>> getReportByDate(
//            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        Map<String, List<PaymentInfo>> report = paymentInfoService.getReportByDateRange(startDate, endDate);
//        return ResponseEntity.ok(report);
//    }
}
