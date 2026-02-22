package com.microservice.inventory_service;

import com.microservice.inventory_service.model.Inventory;
import com.microservice.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(100);
			Inventory inventory_1 = new Inventory();
			inventory_1.setSkuCode("iphone_13_Red");
			inventory_1.setQuantity(10);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory_1);
		};

	}
}
