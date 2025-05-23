package com.packsure.service;

import java.time.LocalDate;
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
		dto.setChannel_order_id(order.getBarcodeNumber());
		dto.setCustomer_name(order.getCustomerName());
		dto.setCustomer_email(order.getCustomerEmail());
		dto.setCustomer_phone(order.getCustomerPhone());
		dto.setCustomer_address(order.getCustomerAddress());
		dto.setChannel_created_at(order.getOrderDate());
		dto.setStatus(order.getStatus());
		dto.setStatus(order.getOrderStatus());

		List<OrderItemDTO> items = order.getItems().stream()
			    .map(item -> {
			        OrderItemDTO itemDTO = new OrderItemDTO();
			        itemDTO.setProduct_id(item.getItemId());
			        itemDTO.setName(item.getItemName());
			        itemDTO.setQuantity(item.getQuantity());
			        itemDTO.setPrice(item.getPricePerUnit());
			        itemDTO.setDiscount(item.getDiscount());
			        itemDTO.setDescription(item.getDescription());
			        itemDTO.setChannel_sku(item.getChannel_sku());
			        return itemDTO;
			    })
			    .collect(Collectors.toList());

		dto.setProducts(items);
		return dto;

	}

	// create order and assign the avilable barcode

	@Transactional
	public Order createOrderWithItems(OrderDTO orderDto) {
		Order order = new Order();
		order.setCustomerName(orderDto.getCustomer_name());
		order.setCustomerAddress(orderDto.getCustomer_address());
		order.setCustomerPhone(orderDto.getCustomer_phone());
		order.setOrderDate(LocalDateTime.now());
		order.setStatus(orderDto.getStatus());

		// Step 1: Create OrderItems
		List<OrderItem> orderItems = orderDto.getProducts().stream().map(itemDto -> {
			OrderItem item = new OrderItem();
			item.setItemName(itemDto.getName());
			item.setQuantity(itemDto.getQuantity());
			item.setPricePerUnit(itemDto.getPrice());
			item.setTotalPrice(itemDto.getQuantity() *itemDto.getPrice());
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

	public Page<OrderDTO> getAllOrders(Pageable pageable, String search,LocalDate startDate,LocalDate endDate) {
		Page<Order> ordersPage;
		
		// convert LocalDate to LocalDateTime for comparison
	    LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
	    LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;
		
	    if (search != null && !search.trim().isEmpty()) {
	        ordersPage = orderRepository.searchOrdersWithDate(search.trim(), startDateTime, endDateTime, pageable);
	    } else {
	        if (startDateTime != null || endDateTime != null) {
	            // when search is empty but date filters are present
	            ordersPage = orderRepository.searchOrdersWithDate("", startDateTime, endDateTime, pageable);
	        } else {
	            ordersPage = orderRepository.findAll(pageable);
	        }
	    }
		
		return ordersPage.map(order -> {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setChannel_order_id(order.getBarcodeNumber());
			orderDTO.setChannel_created_at(order.getOrderDate());
			orderDTO.setPayment_method(order.getPaymentType());
			orderDTO.setCustomer_name(order.getCustomerName());
			orderDTO.setCustomer_address(order.getCustomerAddress());
			orderDTO.setCustomer_phone(order.getCustomerPhone());
			orderDTO.setStatus(order.getStatus());
			orderDTO.setPackedAt(order.getPackedAt());
			orderDTO.setDispactchedAt(order.getDispatchedAt());
			orderDTO.setChannel_name(order.getOrderSource());
			orderDTO.setOrderStatus(order.getOrderStatus());
			orderDTO.setDeliverySource(order.getDeliverySource());
			orderDTO.setChannel_name(order.getOrderSource());
			orderDTO.setPayment_method(order.getPaymentType());
			orderDTO.setPayment_status(order.getPaymentStatus());
		    orderDTO.setCustomer_city(order.getCity());	
		    orderDTO.setCustomer_state(order.getState());
		    orderDTO.setCustomer_address2(order.getAddress2());
    	    orderDTO.setCustomer_country(order.getCountry());
    	    orderDTO.setCustomer_pincode(order.getZipCode());
		    orderDTO.setRto_risk(order.getRto_risk());
		    orderDTO.setTotal(order.getTotal());
		    orderDTO.setTax(order.getTax());
		    orderDTO.setUpdated_at(order.getUpdateAt());
		    orderDTO.setSukStatus(order.getSukStatus());
			return orderDTO;
		});
	}

	public Order getOrderByBarcode(String barcode) throws Exception {

		Order order = orderRepository.findByBarcodeNumber(barcode);
		
		if(!order.getOrderStatus().equalsIgnoreCase("Confirm")) {
			throw new RuntimeException("Order is Not Yet Confirmed.");
		}
		if(order.getOrderStatus().equalsIgnoreCase("Cancel")) {
			throw new RuntimeException("Order is Canceled");
		}

		if ("PACKED".equalsIgnoreCase(order.getSukStatus())) {
			throw new RuntimeException("Order is already packed for this barcode.");
		}
		
		if("DISPATCHED".equalsIgnoreCase(order.getSukStatus())){
			throw new RuntimeException("Order is already dispatched for this barcode.");
		}

		return order; // Fetch the order linked to the barcode
	}

	public void markOrderAsPacked(String barcode) {

		Order order = orderRepository.findByBarcodeNumber(barcode);

		if ("PACKED".equalsIgnoreCase(order.getSukStatus())) {
			throw new IllegalStateException("Order is already packed");
		}

		order.setSukStatus("PACKED");
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

	    String currentStatus = order.getSukStatus();
	    
		if("DISPATCHED".equalsIgnoreCase(order.getSukStatus())){
			throw new BarcodeAlreadyDispatchedException("Order is already dispatched for this barcode.");
		}

	    if ("PACKED".equalsIgnoreCase(currentStatus)) {
	        order.setSukStatus("DISPATCHED");
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
		
		if(!order.getOrderStatus().equalsIgnoreCase("Confirm")) {
			throw new RuntimeException("Order is Not Yet Confirmed.");
		}
		if(order.getOrderStatus().equalsIgnoreCase("Cancel")) {
			throw new RuntimeException("Order is Canceled");
		}
		
		if("PENDING".equalsIgnoreCase(order.getSukStatus())){
			throw new RuntimeException("Order is not packed yet.");
		}
		
		if ("DISPATCHED".equalsIgnoreCase(order.getSukStatus())) {
			throw new RuntimeException("Order is already dispatched for this barcode.");
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

	
//	public List<Order> saveAllOrders(List<OrderDTO> orderDTOs) {
//	    List<Order> newOrders = orderDTOs.stream()
//	        .filter(dto -> !orderRepository.existsByBarcodeNumber(dto.getBarcodeNumber()))
//	        .map(dto -> {
//	            Order order = new Order();
////	            order.setOrderId(dto.getBarcodeNumber());
//	            order.setBarcodeNumber(dto.getBarcodeNumber());
//	            order.setCustomerName(dto.getCustomerName());
//	            order.setCustomerEmail(dto.getCustomerEmail());
//	            order.setCustomerPhone(dto.getCustomerPhone());
//	            order.setCustomerAddress(dto.getCustomerAddress());
//	            order.setOrderDate(dto.getOrderDate());
//	            order.setStatus(dto.getStatus());
//	            order.setPackedAt(dto.getPackedAt());
//	            order.setDispatchedAt(dto.getDispatchedAt());
//	            order.setOrderType(dto.getOrderType());
//	            order.setOrderStatus(dto.getOrderStatus());
//	            order.setOrderSource(dto.getOrderSource());
//	            order.setPaymentType(dto.getPaymentType());
//	            order.setDeliverySource(dto.getDeliverySource());
//	            return order;
//	        })
//	        .collect(Collectors.toList());
//
//	    return orderRepository.saveAll(newOrders);
//	}


	


}
