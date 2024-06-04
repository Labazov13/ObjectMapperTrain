package com.example.ObjectMapperTrain.entities;

import com.example.ObjectMapperTrain.response.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders_table")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @JsonView(View.ResponseOrder.class)
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonView(View.ResponseOrder.class)
    private Customer customer;
    @ManyToMany
    @JoinTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonView(View.ResponseOrder.class)
    private List<Product> productList;
    @Column(name = "order_date")
    @JsonView(View.ResponseOrder.class)
    private LocalDateTime orderDate;
    @Column(name = "shipping_address")
    @JsonView(View.ResponseOrder.class)
    private String shippingAddress;
    @Column(name = "total_price")
    @JsonView(View.ResponseOrder.class)
    private BigDecimal totalPrice;
    @Column(name = "order_status")
    @JsonView(View.ResponseOrder.class)
    private String orderStatus;

    public Order(Customer customer,
                 List<Product> productList,
                 LocalDateTime orderDate, String shippingAddress, BigDecimal totalPrice, String orderStatus) {
        this.customer = customer;
        this.productList = productList;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
}
