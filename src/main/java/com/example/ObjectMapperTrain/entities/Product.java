package com.example.ObjectMapperTrain.entities;

import com.example.ObjectMapperTrain.response.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "products_table")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.ResponseProductFull.class)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    @JsonView(View.ResponseProductForUser.class)
    private String name;
    @JsonView(View.ResponseProductFull.class)
    private String description;
    @Column(name = "price")
    @JsonView(View.ResponseProductForUser.class)
    private BigDecimal cost;
    @Column(name = "quantity_in_stock")
    @JsonView(View.ResponseProductFull.class)
    private Integer quantityInStock;
    @Transient
    @ManyToMany(mappedBy = "productList")
    private List<Order> orders;

    public Product(String name, String description, BigDecimal cost, Integer quantityInStock) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.quantityInStock = quantityInStock;
    }
}
