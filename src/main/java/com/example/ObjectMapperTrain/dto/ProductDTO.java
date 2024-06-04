package com.example.ObjectMapperTrain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductDTO(@NotBlank(message = "Name cannot be empty") String name,
                         @NotBlank(message = "Description cannot be empty") String description,
                         @Min(value = 1, message = "Minimal cost is 1") BigDecimal cost) {
}
