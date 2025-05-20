package com.packsure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.packsure.entity.Order;
import com.packsure.entity.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

	

	Optional<OrderItem> findByOrderIdAndItemId(String barcodeNumber, Long itemId);

	List<OrderItem> findByItemIdIn(List<Long> productIds);

//	Optional<OrderItem> findByItemIdAndOrder_BarcodeNumber(Long itemId, String orderId);

	Optional<OrderItem> findByItemIdAndOrderId(Long itemId, String orderId);

}
