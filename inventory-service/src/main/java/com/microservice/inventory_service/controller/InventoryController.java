package com.microservice.inventory_service.controller;

import com.microservice.inventory_service.service.InventoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory/")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable(name="sku-code") String skuCode){
      return inventoryService.isInStock(skuCode);
    }
}
