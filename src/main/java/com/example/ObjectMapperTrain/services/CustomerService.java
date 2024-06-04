package com.example.ObjectMapperTrain.services;

import com.example.ObjectMapperTrain.entities.Customer;
import com.example.ObjectMapperTrain.exceptions.CustomerNotFoundException;
import com.example.ObjectMapperTrain.repositories.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerService {
    @Autowired
    ObjectMapper objectMapper;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*public Customer createCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(
                new Customer(customerDTO.firstName(), customerDTO.lastName(), customerDTO.email(), customerDTO.contactNumber()));
    }*/
    public Customer createCustomer(Customer customer) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(customer);
        Customer customer1 = objectMapper.readValue(json, Customer.class);
        customer1.setOrders(new ArrayList<>());
        return customerRepository.save(customer1);
    }

    public Customer getCustomer(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }
}
