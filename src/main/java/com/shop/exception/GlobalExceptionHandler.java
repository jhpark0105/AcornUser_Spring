package com.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> response =  new HashMap<>();
        //for (FieldError error : ex.getBindingResult().getFieldErrors()) {
        //    errors.put(error.getField(), error.getDefaultMessage());
        //}
        response.put("error", ex.getMessage());
        response.put("cause", ex.getCause() != null ? ex.getCause().toString() : "No cause");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
