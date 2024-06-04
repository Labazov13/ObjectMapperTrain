package com.example.ObjectMapperTrain.repositories;

import com.example.ObjectMapperTrain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
