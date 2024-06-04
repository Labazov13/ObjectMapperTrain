package com.example.ObjectMapperTrain.controllers;

import com.example.ObjectMapperTrain.dto.ProductDTO;
import com.example.ObjectMapperTrain.entities.Product;
import com.example.ObjectMapperTrain.response.View;
import com.example.ObjectMapperTrain.services.ProductService;
import com.fasterxml.jackson.annotation.JsonView;
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
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.createProduct(productDTO));
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
                                               @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.editProduct(productId, productDTO));
    }
}
