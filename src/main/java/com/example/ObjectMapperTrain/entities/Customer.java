package com.example.ObjectMapperTrain.entities;

import com.example.ObjectMapperTrain.response.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString
@Table(name = "customers_table")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @JsonView(View.ResponseOrder.class)
    private Long customerId;
    @NotBlank(message = "Name cannot be empty")
    @Column(name = "first_name")
    @JsonView(View.ResponseOrder.class)
    private String firstName;
    @NotBlank(message = "Lastname cannot be empty")
    @Column(name = "last_name")
    @JsonView(View.ResponseOrder.class)
    private String lastName;
    @Email(message = "Email is not valid")
    @JsonView(View.ResponseOrder.class)
    private String email;
    @Column(name = "contact_number")
    @JsonView(View.ResponseOrder.class)
    @NotBlank(message = "Phone cannot be empty")
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
