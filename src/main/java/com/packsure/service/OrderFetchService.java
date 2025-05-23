package com.packsure.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.packsure.config.RestTemplateConfig;
import com.packsure.dto.OrderApiResponse;
import com.packsure.dto.OrderDTO;
import com.packsure.dto.OrderItemDTO;
import com.packsure.dto.ShipmentDTO;
import com.packsure.entity.Order;
import com.packsure.entity.OrderItem;
import com.packsure.repository.BarcodePoolRepository;
import com.packsure.repository.OrderRepository;
import com.packsure.entity.BarcodePool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;


@Service
public class OrderFetchService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private BarcodePoolRepository barcodePoolRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    
    
    
    @Autowired
    private RestTemplate restTemplate;
    
    
//    @Transactional
	public void fetchAndSaveOrders(Integer page) {
	    String token = tokenService.getToken();
	    String apiUrl = "https://apiv2.shiprocket.in/v1/external/orders";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(token);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    int totalPages = 1; 
	    int per_page = 400;
	    String pagedUrl = apiUrl + "?page=" + page + "&per_page=" + per_page;
	    System.out.println("Fetching page: " + page);
	    ResponseEntity<Map> response = restTemplate.exchange(pagedUrl, HttpMethod.GET, entity, Map.class);
	    System.out.println("fetched page: " + page);
	
	    if (response.getStatusCode().is2xxSuccessful()) {
	    	Object data = response.getBody().get("data");
	        List<OrderDTO> orders = new ArrayList<>();
	        if (data instanceof List<?>) {
	            for (Object item : (List<?>) data) {
	                OrderDTO dto = objectMapper.convertValue(item, OrderDTO.class);
	                orders.add(dto);
	            }
	        }
	        LocalDateTime lastSavedDate = orderRepository.findMaxOrderDate();
	       
	        for (OrderDTO dto : orders) {
	            LocalDateTime dtoOrderDate = dto.getChannel_created_at();
	//            if (lastSavedDate != null && dtoOrderDate != null && !dtoOrderDate.isAfter(lastSavedDate)) {
	//                System.out.println("Skipping old order: " + dto.getChannel_order_id());
	//                continue;
	//            }
	
	            String newCourier = "by-hand";
	            List<ShipmentDTO> shipments = dto.getShipments();
	            if (shipments != null && !shipments.isEmpty()) {
	                ShipmentDTO firstShipment = shipments.get(0);
	                if (firstShipment.getCourrier() != null && !firstShipment.getCourrier().isBlank()) {
	                    newCourier = firstShipment.getCourrier();
	                }
	            }
	
	            Optional<Order> existingOrderOpt = orderRepository.findOrderByBarcodeNumber(dto.getChannel_order_id());
	            Order order;
	            if (existingOrderOpt.isPresent()) {
	                Order existingOrder = existingOrderOpt.get();
	                String existingCourier = existingOrder.getDeliverySource() != null ? existingOrder.getDeliverySource() : "by-hand";
	                boolean shouldUpdate = !Objects.equals(existingOrder.getStatus(), dto.getStatus())
	                        || !Objects.equals(existingOrder.getPaymentType(), dto.getPayment_method())
	                        || !Objects.equals(existingCourier, newCourier);
	                if (!shouldUpdate) {
	                    System.out.println("Skipping unchanged old order: " + dto.getChannel_order_id());
	                    continue;
	                }
	                order = existingOrder;
	                
	                if (order.getItems() != null) {
	                    order.getItems().clear();
	                }
	                System.out.println("Updating existing order: " + dto.getChannel_order_id());
	            } else {
	                order = new Order();
	                order.setBarcodeNumber(dto.getChannel_order_id());
	                System.out.println("Inserting new order: " + dto.getChannel_order_id());
	            }
	
	            // Common field mapping
	            order.setCustomerName(dto.getCustomer_name());
	            order.setCustomerAddress(dto.getCustomer_address());
	            order.setCity(dto.getCustomer_city());
	            order.setAddress2(dto.getCustomer_address2());
	            order.setCountry(dto.getCustomer_country());
	            order.setStatus(dto.getStatus());
	            order.setState(dto.getCustomer_state());
	            order.setCustomerEmail(dto.getCustomer_email());
	            order.setOrderSource(dto.getChannel_name());
	            order.setZipCode(dto.getCustomer_pincode());
	            order.setPaymentType(dto.getPayment_method());
	            order.setPaymentStatus(dto.getPayment_status());
	            order.setOrderDate(dto.getChannel_created_at());
	            order.setCustomerPhone(dto.getCustomer_phone());
	            order.setRto_risk(dto.getRto_risk());
	            order.setMasterId(dto.getId());
	            order.setUpdateAt(dto.getUpdated_at());
	            order.setTotal(dto.getTotal());
	            order.setTax(dto.getTax());
	            if(dto.getStatus().equalsIgnoreCase("New"))
	            	order.setSukStatus("Pending");
	            else 
	            	order.setSukStatus("Dispatched");
	
	            Boolean isConfirmed = dto.getOthers() != null ? dto.getOthers().getConfirmed() : null;
	            order.setConfirmed(isConfirmed);
	            order.setOrderStatus(Boolean.TRUE.equals(isConfirmed) ? "Confirm" : "Cancel");
	            order.setDeliverySource(newCourier);
	
	            List<OrderItem> itemList = new ArrayList<>();
	            if (dto.getProducts() != null) {
	                for (OrderItemDTO itemDTO : dto.getProducts()) {
	                    OrderItem orderItem = new OrderItem();
	                    orderItem.setChannel_sku(itemDTO.getChannel_sku());
	                    orderItem.setDescription(itemDTO.getDescription());
	                    orderItem.setDiscount(itemDTO.getDiscount());
	                    orderItem.setItemId(itemDTO.getProduct_id());
	                    orderItem.setItemName(itemDTO.getName());
	                    orderItem.setPricePerUnit(itemDTO.getPrice());
	                    orderItem.setQuantity(itemDTO.getQuantity());
	                    orderItem.setOrder(order);
	                    itemList.add(orderItem);
	                }
	            }
	            order.setItems(itemList);
	
	            if (shipments != null && !shipments.isEmpty()) {
	                ShipmentDTO firstShipment = shipments.get(0);
	                order.setProduct_quantity(firstShipment.getProduct_quantity());
	            }
	
	            orderRepository.save(order);
	
	            if (!barcodePoolRepository.existsByBarcodeNumber(dto.getChannel_order_id())) {
	                BarcodePool pool = new BarcodePool();
	                pool.setBarcodeNumber(dto.getChannel_order_id());
	                barcodePoolRepository.save(pool);
	                System.out.println("Saved to BarcodePool: " + dto.getChannel_order_id());
	            } else {
	                System.out.println("Barcode already exists: " + dto.getChannel_order_id());
	            }
	        }
	        Map<String, Object> meta = (Map<String, Object>) response.getBody().get("meta");
	        if (meta != null && meta.get("pagination") instanceof Map) {
	            Map<String, Object> pagination = (Map<String, Object>) meta.get("pagination");
	            if (pagination.get("total_pages") != null) {
	                totalPages = (int) pagination.get("total_pages");
	                if(page < totalPages) {
	                	page++;
	                	fetchAndSaveOrders(page);
	                }
	            }
	        }
	    }
	
	}
}

