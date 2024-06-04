package com.example.ObjectMapperTrain.controllers;

import com.example.ObjectMapperTrain.dto.ProductDTO;
import com.example.ObjectMapperTrain.entities.Product;
import com.example.ObjectMapperTrain.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProductService productService;

    @Test
    void createProduct_ReturnedNewProductWithStatus_OK() throws Exception {
        ProductDTO productDTO = new ProductDTO("Coffee", "Nice coffee", new BigDecimal(100));
        Product product = new Product(1L,
                productDTO.name(),
                productDTO.description(), productDTO.cost(), 1, new ArrayList<>());
        when(productService.createProduct(productDTO)).thenReturn(product);
        String jsonRequest = new ObjectMapper().writeValueAsString(productDTO);
        mockMvc.perform(post("/api/products/new").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.name", is("Coffee")));
    }

    @Test
    void getAllProduct_ReturnedListProductsWithStatus_OK() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L,
                "Coffee",
                "Nice coffee", new BigDecimal(100), 1, new ArrayList<>()));
        productList.add(new Product(2L,
                "Milk",
                "Nice milk", new BigDecimal(50), 1, new ArrayList<>()));
        when(productService.getAllProduct()).thenReturn(productList);
        mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[1].name", is("Milk")));
    }

    @Test
    void getProductWithCorrectId_ReturnedProductWithStatus_OK() throws Exception {
        Product product = new Product(
                1L, "Milk", "Nice milk",
                new BigDecimal(100), 1, new ArrayList<>());
        when(productService.getProduct(product.getProductId())).thenReturn(product);
        mockMvc.perform(get("/api/products/{productId}", product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Milk")));
    }

    @Test
    void deleteProductWithCorrectId_ReturnedStatus_OK() throws Exception {
        Long id = 1L;
        when(productService.deleteProduct(id)).thenReturn("Success!");
        mockMvc.perform(delete("/api/products/delete/{productId}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Success!"));
    }

    @Test
    void editProductAddCorrectChangesAndReturnedStatus_OK() throws Exception {
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO("Cheese", "New description", new BigDecimal(100));
        Product editedProduct = new Product(1L, productDTO.name(),
                productDTO.description(), productDTO.cost(), 1, new ArrayList<>());
        when(productService.editProduct(id, productDTO)).thenReturn(editedProduct);
        String jsonRequest = new ObjectMapper().writeValueAsString(productDTO);
        mockMvc.perform(put("/api/products/edit/{productId}", id)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Cheese")));
    }
}