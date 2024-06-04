package com.example.ObjectMapperTrain.services;

import com.example.ObjectMapperTrain.dto.OrderDTO;
import com.example.ObjectMapperTrain.entities.Customer;
import com.example.ObjectMapperTrain.entities.Order;
import com.example.ObjectMapperTrain.entities.Product;
import com.example.ObjectMapperTrain.exceptions.CustomerNotFoundException;
import com.example.ObjectMapperTrain.exceptions.OrderNotFoundException;
import com.example.ObjectMapperTrain.repositories.CustomerRepository;
import com.example.ObjectMapperTrain.repositories.OrderRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productService = productService;
    }

    public Order createOrder(OrderDTO orderDTO) {
        List<Product> productList = productService.getProductFromOrder(orderDTO.productList());
        BigDecimal totalPrice = productService.getTotalPrice(productList);
        productService.pickUpFromWarehouse(productList);
        Optional<Customer> customer = customerRepository.findById(orderDTO.customerId());
        if (customer.isPresent()) {
            Order order = new Order(
                    customer.get(), productList, LocalDateTime.now(), orderDTO.shippingAddress(), totalPrice, "CREATED");
            return orderRepository.save(order);
        }
        throw new CustomerNotFoundException("Customer not found");
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Order payOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PAID");
        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }
}
