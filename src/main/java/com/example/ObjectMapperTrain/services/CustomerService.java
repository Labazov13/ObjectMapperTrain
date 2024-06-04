package com.example.ObjectMapperTrain.services;

import com.example.ObjectMapperTrain.entities.Customer;
import com.example.ObjectMapperTrain.exceptions.CustomerNotFoundException;
import com.example.ObjectMapperTrain.exceptions.FieldNotFilledException;
import com.example.ObjectMapperTrain.repositories.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService {
    @Autowired
    ObjectMapper objectMapper;
    private final CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*public Customer createCustomer(Customer customer) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(customer);
        Customer customer1 = objectMapper.readValue(json, Customer.class);
        customer1.setOrders(new ArrayList<>());
        return customerRepository.save(customer1);
    }*/

    public Customer createCustomer(String request) throws JsonProcessingException{
        Customer customer = objectMapper.readValue(request, Customer.class);
        if (checkCustomer(customer, "partial")){
            customer.setOrders(new ArrayList<>());
            return customerRepository.save(customer);
        }
        throw new FieldNotFilledException("FIELD NOT FILLED");
    }

    public boolean checkCustomer(Customer customer, String constructorType) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        return violations.isEmpty() && constructorType.equals("partial");
    }

    public Customer getCustomer(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }
}
