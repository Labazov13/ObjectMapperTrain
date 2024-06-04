package com.example.ObjectMapperTrain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(@NotBlank(message = "Name cannot be empty") String firstName,
                          @NotBlank(message = "Name cannot be empty") String lastName,
                          @Email(message = "Email is not valid") String email,
                          @NotBlank(message = "Phone cannot be empty") String contactNumber) {
}
