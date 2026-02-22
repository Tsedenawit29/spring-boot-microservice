package com.microservice.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class OrderSeerviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderSeerviceApplication.class, args);
	}

}
