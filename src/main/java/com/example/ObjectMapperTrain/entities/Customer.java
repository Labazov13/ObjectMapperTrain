package com.example.ObjectMapperTrain.entities;

import com.example.ObjectMapperTrain.response.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "customers_table")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @JsonView(View.ResponseOrder.class)
    private Long customerId;
    @Column(name = "first_name")
    @JsonView(View.ResponseOrder.class)
    private String firstName;
    @Column(name = "last_name")
    @JsonView(View.ResponseOrder.class)
    private String lastName;
    @JsonView(View.ResponseOrder.class)
    private String email;
    @Column(name = "contact_number")
    @JsonView(View.ResponseOrder.class)
    private String contactNumber;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Customer(String firstName, String lastName, String email, String contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
    }
}
