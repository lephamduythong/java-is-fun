package com.example.springboottemplate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api2/hello")
    public String hello() {
        return "Hello, Spring Boot with Java 11!";
    }

}
