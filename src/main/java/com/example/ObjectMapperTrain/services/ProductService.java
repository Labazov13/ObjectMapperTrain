package com.example.ObjectMapperTrain.services;

import com.example.ObjectMapperTrain.dto.ProductDTO;
import com.example.ObjectMapperTrain.entities.Product;
import com.example.ObjectMapperTrain.exceptions.ProductNotFoundException;
import com.example.ObjectMapperTrain.repositories.ProductRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Product createProduct(ProductDTO productDTO) {
        List<Product> productList = productRepository.findAll();
        boolean check = productList.stream().anyMatch(product -> product.getName().equals(productDTO.name()));
        if (check) {
            Product product = productRepository.findByName(productDTO.name());
            product.setQuantityInStock(product.getQuantityInStock() + 1);
            return productRepository.save(product);

        }
        Product product = new Product(productDTO.name(), productDTO.description(), productDTO.cost(), 1);
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
    public Product editProduct(Long productId, ProductDTO productDTO){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found"));
        product.setName(productDTO.name());
        product.setDescription(productDTO.description());
        product.setCost(productDTO.cost());
        return productRepository.save(product);
    }
}
