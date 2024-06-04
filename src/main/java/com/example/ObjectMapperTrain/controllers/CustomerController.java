package com.example.ObjectMapperTrain.controllers;

import com.example.ObjectMapperTrain.entities.Customer;
import com.example.ObjectMapperTrain.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@RequestBody String request) throws JsonProcessingException {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@PathVariable(value = "customerId") Long customerId){
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }
}
