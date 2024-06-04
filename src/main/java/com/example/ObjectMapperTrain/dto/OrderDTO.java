package com.example.ObjectMapperTrain.dto;

import java.util.List;

public record OrderDTO(Long customerId, List<String> productList, String shippingAddress) {
}
