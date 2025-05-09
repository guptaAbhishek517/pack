package com.packsure.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.packsure.dto.OrderDTO;
import com.packsure.entity.Order;
import com.packsure.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/{orderId}/items")
	public ResponseEntity<OrderDTO> getOrderItems(@PathVariable Long orderId) {
		OrderDTO items = orderService.getOrderItemsByOrderId(orderId);
		return ResponseEntity.ok(items);
	}

	@PostMapping("/create")
	public ResponseEntity<Order> createOrderDetails(@RequestBody OrderDTO orderDto) {
		Order order = orderService.createOrderWithItems(orderDto);
		return ResponseEntity.ok(order);

	}

	// api endpoint to get orders by barcodeNumber
	@GetMapping("/by-barcode/{barcode}")
	public ResponseEntity<Order> getOrderByBarcode(@PathVariable String barcode) {
		Order order = orderService.getOrderByBarcode(barcode);
		return ResponseEntity.ok(order);
	}

	// api endpoint to make order pack
	@PutMapping("/{barcode}/pack")
	public ResponseEntity<?> markOrderAsPacked(@PathVariable String barcode) {
		try {
			orderService.markOrderAsPacked(barcode);
			Map<String, String> response = new HashMap<>();
			response.put("message", "Order Packed");
			return ResponseEntity.ok(response);
		} catch (IllegalStateException e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		} catch (RuntimeException e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Something went wrong");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}

	// api endpoint to get OrderId
	@GetMapping("/all")
	public Page<OrderDTO> getAllOrderID(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return orderService.getAllOrders(pageable);
	}

	// api endpoint to make orderID dispatched
	@PutMapping("/{barcode}/dispatch")
	public ResponseEntity<Map<String, String>> markOrderAsDispatched(@PathVariable String barcode) {
		Map<String, String> result = orderService.markOrderAsDispatched(barcode);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("by-barcode/{barcode}/before")
	public ResponseEntity<Order> getOrderByBarcodeBeforeDispatch(@PathVariable String barcode){
		Order order = orderService.getOrderByBarcodeBeforeDispatch(barcode);
		return ResponseEntity.ok(order);
	}

}
