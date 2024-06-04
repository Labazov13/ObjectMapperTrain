package com.example.ObjectMapperTrain.entities;

import com.example.ObjectMapperTrain.response.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@ToString
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
    @NotBlank(message = "Name cannot be empty")
    @JsonView(View.ResponseProductForUser.class)
    private String name;
    @JsonView(View.ResponseProductFull.class)
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @Column(name = "price")
    @JsonView(View.ResponseProductForUser.class)
    @Min(value = 1, message = "Minimal cost is 1")
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

    public Product(String name, String description, BigDecimal cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }
}
