package com.kt.backendapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        return "Hello, Spring Boot is running!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
} 