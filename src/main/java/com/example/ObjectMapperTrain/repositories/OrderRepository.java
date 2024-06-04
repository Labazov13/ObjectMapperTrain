package com.example.ObjectMapperTrain.repositories;

import com.example.ObjectMapperTrain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
