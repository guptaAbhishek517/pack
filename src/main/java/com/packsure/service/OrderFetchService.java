package com.packsure.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.packsure.dto.OrderApiResponse;
import com.packsure.dto.OrderDTO;
import com.packsure.dto.OrderItemDTO;
import com.packsure.dto.ShipmentDTO;
import com.packsure.entity.Order;
import com.packsure.entity.OrderItem;
import com.packsure.repository.BarcodePoolRepository;
import com.packsure.repository.OrderRepository;
import com.packsure.entity.BarcodePool;

@Service
public class OrderFetchService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private BarcodePoolRepository barcodePoolRepository;

    public void fetchAndSaveOrders() {
        String token = tokenService.getToken();
        String apiUrl = "https://apiv2.shiprocket.in/v1/external/orders";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<OrderApiResponse> response = new RestTemplate()
                .exchange(apiUrl, HttpMethod.GET, entity, OrderApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            List<OrderDTO> orders = response.getBody().getData();
            
            System.out.println(orders);

            
            LocalDateTime lastSavedDate = orderRepository.findMaxOrderDate();
            System.out.println("Last saved order date: " + lastSavedDate);

            for (OrderDTO dto : orders) {
                
                LocalDateTime dtoOrderDate = dto.getOrderDate();

                
                if (lastSavedDate != null && dtoOrderDate != null && !dtoOrderDate.isAfter(lastSavedDate)) {
                    System.out.println("Skipping old order: " + dto.getBarcodeNumber());
                    continue;
                }

               
                if (!orderRepository.existsByBarcodeNumber(dto.getBarcodeNumber())) {
                	
                	
                    Order order = new Order();
                    
                    order.setBarcodeNumber(dto.getBarcodeNumber());
                    order.setCustomerName(dto.getCustomerName());
                    order.setOrderStatus(dto.getStatus());
                    order.setCustomerAddress(dto.getCustomerAddress());
                    order.setCity(dto.getCity());
                    order.setAddress2(dto.getAddress2());
                    order.setCountry(dto.getCountry());
                    order.setStatus(dto.getStatus());
                    order.setState(dto.getState());
                    order.setCustomerEmail(dto.getCustomerEmail());
                    order.setOrderSource(dto.getOrderSource());
                    order.setZipCode(dto.getZipCode());
                    order.setPaymentType(dto.getPaymentType());
                    order.setPaymentStatus(dto.getPaymentStatus());
                    order.setOrderDate(dto.getOrderDate()); // very important
                    order.setCustomerPhone(dto.getCustomerPhone());
                    order.setRto_risk(dto.getRto_risk());
                    

                    List<OrderItem> itemList = new ArrayList<>();
                    if (dto.getItems() != null) {
                        for (OrderItemDTO itemDTO : dto.getItems()) {
                            OrderItem item = new OrderItem();
                            item.setItemName(itemDTO.getItemName());
                            item.setQuantity(itemDTO.getQuantity());
                            item.setPricePerUnit(itemDTO.getPricePerUnit());
                            item.setOrder(order);
                            itemList.add(item);
                         }
                    }
                    
                    List<ShipmentDTO> shipments = dto.getShipments();

                    if (shipments != null && !shipments.isEmpty()) {
                        ShipmentDTO firstShipment = shipments.get(0);
                        String courier = firstShipment.getCourrier();  
                        System.out.println(courier);
                        order.setDeliverySource(courier != null && !courier.isEmpty() ? courier : "by-hand");
                    } else {
                        order.setDeliverySource("by-hand");
                    }

                  
                    order.setItems(itemList);
                   
                    orderRepository.save(order);
                    
                    if (!barcodePoolRepository.existsByBarcodeNumber(dto.getBarcodeNumber())) {
                        BarcodePool pool = new BarcodePool();
                        pool.setBarcodeNumber(dto.getBarcodeNumber());
                        barcodePoolRepository.save(pool);
                        System.out.println("Saved to BarcodePool: " + dto.getBarcodeNumber());
                    }
                    
                    System.out.println("Saved order: " + order.getBarcodeNumber());
                } else {
                    System.out.println("Order already exists: " + dto.getBarcodeNumber());
                }
            }
        } else {
            System.out.println("API call failed: " + response.getStatusCode());
        }
    }
}

        
    




