package com.packsure.globalexceptionhandler;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.packsure.exception.BarcodeAlreadyDispatchedException;
import com.packsure.exception.BarcodeAlreadyPackedException;
import com.packsure.exception.BarcodeNotFoundException;
import com.packsure.exception.DatabaseException;
import com.packsure.exception.NoDataFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BarcodeAlreadyDispatchedException.class)
	public ResponseEntity<?> handleBarcodeAlreadyDispatchedException(BarcodeAlreadyDispatchedException ex) {
	    Map<String, String> response = new HashMap<>();
	    response.put("message", ex.getMessage());
	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(response);
	}
	
    @ExceptionHandler(BarcodeNotFoundException.class)
    public ResponseEntity<?> handleBarcodeNotFound(BarcodeNotFoundException ex) {
    	Map<String, String> response = new HashMap<>();
    	response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BarcodeAlreadyPackedException.class)
    public ResponseEntity<?> handleBarcodePacked(BarcodeAlreadyPackedException ex) {
    	Map<String, String> response = new HashMap<>();
    	response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<?> handleDatabase(DatabaseException ex) {
    	Map<String, String> response = new HashMap<>();
    	response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> handleNoDatafoundException(NoDataFoundException ex) {
    	Map<String, String> response = new HashMap<>();
    	response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
    	Map<String, String> response = new HashMap<>();
    	response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    
  
}
