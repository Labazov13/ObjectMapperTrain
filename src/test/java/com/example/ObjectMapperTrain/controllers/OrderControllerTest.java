package com.example.ObjectMapperTrain.controllers;

import com.example.ObjectMapperTrain.dto.OrderDTO;
import com.example.ObjectMapperTrain.entities.Customer;
import com.example.ObjectMapperTrain.entities.Order;
import com.example.ObjectMapperTrain.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;

    @Test
    void createOrder_ReturnedNewOrderWithStatus_OK() throws Exception {
        OrderDTO orderDTO = new OrderDTO(1L, new ArrayList<>(), "Barnaul");
        Order order = new Order(1L, new Customer(1L,
                "Petr", "Petrov",
                "petr@mail.ru", "111", new ArrayList<>()),
                new ArrayList<>(), LocalDateTime.now(),
                "Barnaul", new BigDecimal(125), "CREATED");
        when(orderService.createOrder(orderDTO)).thenReturn(order);
        String jsonRequest = new ObjectMapper().writeValueAsString(orderDTO);
        mockMvc.perform(post("/api/orders/new").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(1)))
                .andExpect(jsonPath("$.shippingAddress", is("Barnaul")));
    }

    @Test
    void getOrderWithCorrectId_ReturnedOrderWithStatus_OK() throws Exception {
        Order order = new Order(1L, new Customer(1L,
                "Petr", "Petrov",
                "petr@mail.ru", "111", new ArrayList<>()),
                new ArrayList<>(), LocalDateTime.now(),
                "Barnaul", new BigDecimal(125), "CREATED");
        when(orderService.getOrder(order.getOrderId())).thenReturn(order);
        mockMvc.perform(get("/api/orders/{orderId}", order.getOrderId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus", is("CREATED")));
    }
}