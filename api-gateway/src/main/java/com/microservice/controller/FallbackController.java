package com.microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/product")
    public String productFallback() {
        return "Product Service is currently unavailable.";
    }

    @GetMapping("/fallback/order")
    public String orderFallback() {
        return "Order Service is currently unavailable.";
    }

    @GetMapping("/fallback/inventory")
    public String inventoryFallback() {
        return "Inventory Service is currently unavailable.";
    }
}
