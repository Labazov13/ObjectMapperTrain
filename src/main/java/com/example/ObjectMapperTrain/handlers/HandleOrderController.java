package com.example.ObjectMapperTrain.handlers;

import com.example.ObjectMapperTrain.exceptions.OrderNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleOrderController {
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
