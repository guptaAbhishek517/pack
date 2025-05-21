package com.packsure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.packsure.entity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findByBarcodeNumber(String barcode);
	
	List<Order> findAllByBarcodeNumber(String barcode);

	boolean existsByBarcodeNumber(String barcode);
	
	@Query("SELECT MAX(o.orderDate) FROM Order o")
	LocalDateTime findMaxOrderDate();

	Optional<Order> findOrderByBarcodeNumber(String barcodeNumber);
	
	@Query("SELECT o FROM Order o WHERE " +
	           "LOWER(o.barcodeNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	           "LOWER(o.customerName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	           "LOWER(o.customerAddress) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	           "LOWER(o.paymentType) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	           "LOWER(o.status) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Order> searchOrders(@Param("search") String search, Pageable pageable);

	
	

}
