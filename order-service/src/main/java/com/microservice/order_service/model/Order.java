package com.microservice.order_service.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList;
}