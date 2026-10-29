package com.ganado.usuarios.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handle(Exception ex) {

        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(body);
    }
}
