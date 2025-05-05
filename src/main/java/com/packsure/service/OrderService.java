package com.packsure.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.packsure.dto.OrderItemDTO;
import com.packsure.entity.BarcodePool;
import com.packsure.entity.Order;
import com.packsure.entity.OrderItem;
import com.packsure.exception.BarcodeAlreadyDispatchedException;
import com.packsure.exception.BarcodeAlreadyPackedException;
import com.packsure.exception.ResourceNotFoundException;
import com.packsure.repository.BarcodePoolRepository;
//import com.packsure.repository.BarcodeRepository;
import com.packsure.repository.OrderRepository;

import jakarta.transaction.Transactional;

import com.packsure.dto.OrderDTO;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BarcodePoolRepository barcodepoolRepo;

	public OrderDTO getOrderItemsByOrderId(Long orderId) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		OrderDTO dto = new OrderDTO();
		dto.setOrderId(order.getBarcodeNumber());
		dto.setCustomerName(order.getCustomerName());
		dto.setCustomerEmail(order.getCustomerEmail());
		dto.setCustomerPhone(order.getCustomerPhone());
		dto.setCustomerAddress(order.getCustomerAddress());
		dto.setOrderDate(order.getOrderDate());
		dto.setStatus(order.getStatus());
		dto.setStatus(order.getOrderStatus());

		List<OrderItemDTO> items = order.getItems().stream()
			    .map(item -> {
			        OrderItemDTO itemDTO = new OrderItemDTO();
			        itemDTO.setItemId(item.getId());
			        itemDTO.setItemName(item.getItemName());
			        itemDTO.setQuantity(item.getQuantity());
			        itemDTO.setPricePerUnit(item.getPricePerUnit());
			        itemDTO.setTotalPrice(item.getTotalPrice());
			        return itemDTO;
			    })
			    .collect(Collectors.toList());

		dto.setItems(items);
		return dto;

	}

	// create order and assign the avilable barcode

	@Transactional
	public Order createOrderWithItems(OrderDTO orderDto) {
		Order order = new Order();
		order.setCustomerName(orderDto.getCustomerName());
		order.setCustomerAddress(orderDto.getCustomerAddress());
		order.setCustomerPhone(orderDto.getCustomerPhone());
		order.setOrderDate(LocalDateTime.now());
		order.setStatus(orderDto.getStatus());

		// Step 1: Create OrderItems
		List<OrderItem> orderItems = orderDto.getItems().stream().map(itemDto -> {
			OrderItem item = new OrderItem();
			item.setItemName(itemDto.getItemName());
			item.setQuantity(itemDto.getQuantity());
			item.setPricePerUnit(itemDto.getPricePerUnit());
			item.setTotalPrice(itemDto.getQuantity() * itemDto.getPricePerUnit());
			item.setOrder(order);
			return item;
		}).collect(Collectors.toList());
		order.setItems(orderItems);

		// Step 2: Get an unused BarcodePool entry
		BarcodePool pool = barcodepoolRepo.findFirstByIsUsedFalse()
				.orElseThrow(() -> new RuntimeException("No available barcodes in pool"));

		// Step 3: Create new Barcode from the pool
		order.setBarcodeNumber(pool.getBarcodeNumber());

		order.setBarcodepool(pool);

		// Step 6: Save Order (cascades OrderItems)
		Order savedOrder = orderRepository.save(order);

		// Step 7: Mark BarcodePool as used
		pool.setUsed(true);
		barcodepoolRepo.save(pool);

		return savedOrder;
	}

	public Page<OrderDTO> getAllOrders(Pageable pageable) {
		Page<Order> ordersPage = orderRepository.findAll(pageable);

		return ordersPage.map(order -> {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderId(order.getBarcodeNumber());
			orderDTO.setOrderDate(order.getOrderDate());
			orderDTO.setPaymentType(order.getPaymentType());
			orderDTO.setCustomerName(order.getCustomerName());
			orderDTO.setCustomerAddress(order.getCustomerAddress());
			orderDTO.setCustomerPhone(order.getCustomerPhone());
			orderDTO.setStatus(order.getStatus());
			orderDTO.setPackedAt(order.getPackedAt());
			orderDTO.setDispatchedAt(order.getDispatchedAt());
			orderDTO.setOrderSource(order.getOrderSource());
			orderDTO.setOrderStatus(order.getOrderStatus());
			orderDTO.setDeliverySource(order.getDeliverySource());
			return orderDTO;
		});
	}

	public Order getOrderByBarcode(String barcode) {

		Order order = orderRepository.findByBarcodeNumber(barcode);

		if ("PACKED".equalsIgnoreCase(order.getStatus())) {
			throw new BarcodeAlreadyPackedException("Order is already packed for this barcode.");
		}
		
		if("DISPATCHED".equalsIgnoreCase(order.getStatus())){
			throw new BarcodeAlreadyPackedException("Order is already dispatched for this barcode.");
		}

		return order; // Fetch the order linked to the barcode
	}

	public void markOrderAsPacked(String barcode) {

		Order order = orderRepository.findByBarcodeNumber(barcode);

		if ("PACKED".equalsIgnoreCase(order.getStatus())) {
			throw new IllegalStateException("Order is already packed");
		}

		order.setStatus("PACKED");
		order.setPackedAt(LocalDateTime.now());
		orderRepository.save(order);
	}

	public Map<String, String> markOrderAsDispatched(String barcode) {
	    Map<String, String> response = new HashMap<>();

	    Order order = orderRepository.findByBarcodeNumber(barcode);
	    if (order == null) {
	        response.put("status", "NOT_FOUND");
	        response.put("message", "Order not found for barcode: " + barcode);
	        return response;
	    }

	    String currentStatus = order.getStatus();
	    
		if("DISPATCHED".equalsIgnoreCase(order.getStatus())){
			throw new BarcodeAlreadyDispatchedException("Order is already dispatched for this barcode.");
		}

	    if ("PACKED".equalsIgnoreCase(currentStatus)) {
	        order.setStatus("DISPATCHED");
	        order.setDispatchedAt(LocalDateTime.now());
	        orderRepository.save(order);

	        response.put("status", "DISPATCHED");
	        response.put("message", "Order dispatched successfully.");
	        return response;
	    }

	    response.put("status", "INVALID_STATUS");
	    response.put("message", "Order must be in 'PACKED' status to dispatch. Current status: " + currentStatus);
	    return response;
	}

}
