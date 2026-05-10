package com.example.springboottemplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() throws InterruptedException {
        // Simulate slow data retrieval
        Thread.sleep(2000);

        Map<String, Object> data = new HashMap<>();
        data.put("id", 1);
        data.put("name", "Sample Info");
        data.put("status", "ok");

        return ResponseEntity.ok(data);
    }
}
