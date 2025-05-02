package com.packsure.globalexceptionhandler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.packsure.exception.BarcodeAlreadyDispatchedException;
import com.packsure.exception.BarcodeAlreadyPackedException;
import com.packsure.exception.BarcodeNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BarcodeAlreadyDispatchedException.class)
    public ResponseEntity<?> handleBarcodeAlreadyDispatchedException(BarcodeAlreadyDispatchedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }
    
    @ExceptionHandler(BarcodeNotFoundException.class)
    public ResponseEntity<?> handleBarcodeNotFound(BarcodeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(BarcodeAlreadyPackedException.class)
    public ResponseEntity<?> handleBarcodePacked(BarcodeAlreadyPackedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Something went wrong"));
    }
   
}
