package com.example.ObjectMapperTrain.controllers;

import com.example.ObjectMapperTrain.entities.Product;
import com.example.ObjectMapperTrain.response.View;
import com.example.ObjectMapperTrain.services.ProductService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.ResponseProductFull.class)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product productRequest) throws JsonProcessingException {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.ResponseProductFull.class)
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.ResponseProductFull.class)
    public ResponseEntity<Product> getProduct(@PathVariable(name = "productId") Long productId){
        return ResponseEntity.ok(productService.getProduct(productId));
    }
    @DeleteMapping(value = "/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") Long productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
    @PutMapping(value = "/edit/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> editProduct(@PathVariable(name = "productId") Long productId,
                                               @Valid @RequestBody Product productRequest){
        return ResponseEntity.ok(productService.editProduct(productId, productRequest));
    }
}
