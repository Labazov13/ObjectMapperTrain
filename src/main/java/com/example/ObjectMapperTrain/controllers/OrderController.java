package com.example.ObjectMapperTrain.controllers;

import com.example.ObjectMapperTrain.dto.OrderDTO;
import com.example.ObjectMapperTrain.entities.Order;
import com.example.ObjectMapperTrain.response.View;
import com.example.ObjectMapperTrain.services.OrderService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @JsonView(View.ResponseOrder.class)
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @PutMapping(value = "/pay/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.ResponseOrder.class)
    public ResponseEntity<Order> payForOrder(@PathVariable(value = "orderId") Long orderId){
        return ResponseEntity.ok(orderService.payOrder(orderId));
    }
    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.ResponseOrder.class)
    public ResponseEntity<Order> getOrder(@PathVariable(value = "orderId") Long orderId){
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}
