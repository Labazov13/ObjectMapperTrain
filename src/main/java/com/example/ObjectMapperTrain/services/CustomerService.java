package com.example.ObjectMapperTrain.services;

import com.example.ObjectMapperTrain.dto.CustomerDTO;
import com.example.ObjectMapperTrain.entities.Customer;
import com.example.ObjectMapperTrain.exceptions.CustomerNotFoundException;
import com.example.ObjectMapperTrain.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(
                new Customer(customerDTO.firstName(), customerDTO.lastName(), customerDTO.email(), customerDTO.contactNumber()));
    }

    public Customer getCustomer(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }
}
