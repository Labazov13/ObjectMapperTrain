package com.example.ObjectMapperTrain.repositories;

import com.example.ObjectMapperTrain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
