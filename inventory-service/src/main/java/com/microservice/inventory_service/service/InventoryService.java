package com.microservice.inventory_service.service;

import com.microservice.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional
    public boolean isInStock(String skuCode){
       return  inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
