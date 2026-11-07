package com.example.springboottemplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.springboottemplate.pool.RequestThreadPoolManager;
import com.example.springboottemplate.service.CaffeineCacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/test")
public class TestController {
    private final CaffeineCacheService cacheService = CaffeineCacheService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RequestThreadPoolManager reqPool = RequestThreadPoolManager.getInstance();

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

    @GetMapping(value = "/cache/{recid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRecord(@PathVariable String recid) throws IOException {
        
        reqPool.runTask(() -> {
            String xmlRecord = cacheService.getXmlRecord(recid);
            if (xmlRecord == null) {
                System.out.println("Không tìm thấy record");
                return; // thoát khỏi Runnable, không trả về giá trị
            }
            System.out.println(xmlRecord);
        });

        // Thread thread = new Thread(() -> {
        //     String xmlRecord = cacheService.getXmlRecord(recid);
        //     if (xmlRecord == null) {
        //         System.out.println("Không tìm thấy record");
        //         return; // thoát khỏi Runnable, không trả về giá trị
        //     }
        //     System.out.println(xmlRecord);
        // });
        // thread.start();
        
        return ResponseEntity.ok("OK");
        // return ResponseEntity.ok(objectMapper.readTree(xmlRecord));
    }
}
