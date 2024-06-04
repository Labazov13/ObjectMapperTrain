package com.example.ObjectMapperTrain.services;

import com.example.ObjectMapperTrain.entities.Product;
import com.example.ObjectMapperTrain.exceptions.ProductNotFoundException;
import com.example.ObjectMapperTrain.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Product createProduct(Product productRequest) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(productRequest);
        List<Product> productList = productRepository.findAll();
        boolean check = productList.stream().anyMatch(product -> product.getName().equals(productRequest.getName()));
        if (check) {
            Product product = productRepository.findByName(productRequest.getName());
            product.setQuantityInStock(product.getQuantityInStock() + 1);
            return productRepository.save(product);

        }
        Product product = objectMapper.readValue(json, Product.class);
        product.setQuantityInStock(1);
        return productRepository.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void pickUpFromWarehouse(List<Product> productList){
        for (Product product : productList){
            product.setQuantityInStock(product.getQuantityInStock() - 1);
            productRepository.save(product);
        }
    }


    public List<Product> getProductFromOrder(List<String> list){
        List<Product> productList = getAllProduct();
        return productList.stream().filter(product -> list.contains(product.getName())).collect(Collectors.toList());
    }
    public Product getProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public BigDecimal getTotalPrice(List<Product> productList){
        return productList.stream().map(Product::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
        return "Success!";
    }
    public String deleteProduct(String productName){
        Product product = productRepository.findByName(productName);
        productRepository.delete(product);
        return "Success!";
    }
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Product editProduct(Long productId, Product productRequest){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCost(productRequest.getCost());
        return productRepository.save(product);
    }
}
