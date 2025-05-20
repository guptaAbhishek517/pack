package com.packsure.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.packsure.dto.OrderDTO;
import com.packsure.dto.OrderItemDTO;
import com.packsure.entity.BarcodePool;
import com.packsure.entity.Order;
import com.packsure.entity.OrderItem;
import com.packsure.exception.BarcodeAlreadyDispatchedException;
import com.packsure.exception.BarcodeAlreadyPackedException;
import com.packsure.exception.BarcodeNotFoundException;
import com.packsure.exception.ResourceNotFoundException;
import com.packsure.repository.BarcodePoolRepository;
//import com.packsure.repository.BarcodeRepository;
import com.packsure.repository.OrderRepository;
import java.util.*;

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
			orderDTO.setOrderSource(order.getOrderSource());
			orderDTO.setPaymentType(order.getPaymentType());
			orderDTO.setPaymentStatus(order.getPaymentStatus());
		    orderDTO.setCity(order.getCity());	
		    orderDTO.setState(order.getState());
		    orderDTO.setAddress2(order.getAddress2());
    	    orderDTO.setCountry(order.getCountry());
    	    orderDTO.setZipCode(order.getZipCode());
		    
		    System.out.println(order.getZipCode());
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

	public Order getOrderByBarcodeBeforeDispatch(String barcode) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findByBarcodeNumber(barcode);
		
		if ("DISPATCHED".equalsIgnoreCase(order.getStatus())) {
			throw new BarcodeAlreadyDispatchedException("Order is already packed for this barcode.");
		}
		
		if("PENDING".equalsIgnoreCase(order.getStatus())){
			throw new BarcodeAlreadyPackedException("Order is not packed yet.");
		}

		return order; // Fetch the order linked to the barcode
		
	}

	
	public void updateOrderStatus(String orderId, String orderStatus , String cancelationReason) {
	    System.out.println("Service Layer : "+ orderId);
	    System.out.println(orderStatus);
	    System.out.println(cancelationReason);

	    Order order = orderRepository.findByBarcodeNumber(orderId);
	    
	    if (order != null) {
	        order.setOrderStatus(orderStatus);
	        order.setCancelReason(cancelationReason);
	        orderRepository.save(order);
	    } else {
	        throw new RuntimeException("Order not found with orderId: " + orderId);
	    }
	}

	public void updateDeliverySourceStatus(String orderId, String deliverySource) {
		System.out.println(orderId);
		System.out.println(deliverySource);
		
		Order order = orderRepository.findByBarcodeNumber(orderId);
		
		if(order !=null) {
			order.setDeliverySource(deliverySource);
			orderRepository.save(order);
		}else {
			throw new RuntimeException("Order not found with orderID: " + orderId);
		}	
	}

	
	public List<Order> saveAllOrders(List<OrderDTO> orderDTOs) {
	    List<Order> newOrders = orderDTOs.stream()
	        .filter(dto -> !orderRepository.existsByBarcodeNumber(dto.getBarcodeNumber()))
	        .map(dto -> {
	            Order order = new Order();
//	            order.setOrderId(dto.getBarcodeNumber());
	            order.setBarcodeNumber(dto.getBarcodeNumber());
	            order.setCustomerName(dto.getCustomerName());
	            order.setCustomerEmail(dto.getCustomerEmail());
	            order.setCustomerPhone(dto.getCustomerPhone());
	            order.setCustomerAddress(dto.getCustomerAddress());
	            order.setOrderDate(dto.getOrderDate());
	            order.setStatus(dto.getStatus());
	            order.setPackedAt(dto.getPackedAt());
	            order.setDispatchedAt(dto.getDispatchedAt());
	            order.setOrderType(dto.getOrderType());
	            order.setOrderStatus(dto.getOrderStatus());
	            order.setOrderSource(dto.getOrderSource());
	            order.setPaymentType(dto.getPaymentType());
	            order.setDeliverySource(dto.getDeliverySource());
	            return order;
	        })
	        .collect(Collectors.toList());

	    return orderRepository.saveAll(newOrders);
	}


	


}
