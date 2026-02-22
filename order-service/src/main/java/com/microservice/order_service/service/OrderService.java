package com.microservice.order_service.service;

import com.microservice.order_service.dto.InventoryResponse;
import com.microservice.order_service.dto.OrderLineItemsDto;
import com.microservice.order_service.dto.OrderRequest;
import com.microservice.order_service.model.Order;
import com.microservice.order_service.model.OrderLineItems;
import com.microservice.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @CircuitBreaker(name = "inventoryServiceCircuitBreaker", fallbackMethod = "fallbackPlaceOrder")
    public String placeOrder(OrderRequest orderRequest) {

        log.info("Starting order placement process...");

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToEntity)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        log.info("Calling Inventory Service for SKUs: {}", skuCodes);

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build()
                .get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        log.info("Received response from Inventory Service");

        if (inventoryResponseArray == null || inventoryResponseArray.length == 0) {
            throw new IllegalArgumentException("No inventory found for given SKUs");
        }

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            throw new IllegalArgumentException("Product is not in stock");
        }

        orderRepository.save(order);

        log.info("Order saved successfully!");

        return "Order placed successfully";
    }

    private OrderLineItems mapToEntity(OrderLineItemsDto dto) {
        OrderLineItems item = new OrderLineItems();
        item.setSkuCode(dto.getSkuCode());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        return item;
    }

    public String fallbackPlaceOrder(OrderRequest orderRequest, Throwable throwable) {
        log.error("Inventory service is down: {}", throwable.getMessage());
        return "Inventory Service is currently unavailable.";
    }

}
