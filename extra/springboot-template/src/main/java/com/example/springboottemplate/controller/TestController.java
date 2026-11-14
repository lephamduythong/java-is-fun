package com.example.springboottemplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import com.example.springboottemplate.pool.RequestThreadPoolManager;
import com.example.springboottemplate.repository.T24CoreRepositoryImpl;
import com.example.springboottemplate.service.CaffeineCacheService;
import com.example.springboottemplate.service.T24DBService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/test")
public class TestController {
    private final CaffeineCacheService cacheService = CaffeineCacheService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RequestThreadPoolManager reqPool = RequestThreadPoolManager.getInstance();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    
    private AtomicLong counter = new AtomicLong(0);

    private final T24CoreRepositoryImpl t24CoreRepository;
    private final T24DBService t24DBService;

    public TestController() {
        t24CoreRepository = new T24CoreRepositoryImpl();
        t24DBService = new T24DBService(t24CoreRepository);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() throws InterruptedException {
        // Simulate slow data retrieval
        // Thread.sleep(2000);

        Map<String, Object> data = new HashMap<>();
        data.put("id", 1);
        data.put("name", "Sample Info");
        data.put("status", "ok");

        return ResponseEntity.ok(data);
    }

    @GetMapping(value = "/cache/sync/{recid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCacheRecord1(@PathVariable String recid) throws IOException {
        String xmlRecord = cacheService.getXmlRecord(recid);
        if (xmlRecord == null) {
            System.out.println("Không tìm thấy record");
            return ResponseEntity.notFound().build();
        }
        System.out.println(xmlRecord);
        if (xmlRecord != null && !xmlRecord.isEmpty() && xmlRecord.contains("a")) {
            counter.incrementAndGet(); // tăng counter mỗi khi tìm thấy record
        }
        return ResponseEntity.ok("OK");
        // return ResponseEntity.ok(objectMapper.readTree(xmlRecord));
    }

    @GetMapping(value = "/cache/async/{recid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCacheRecord2(@PathVariable String recid) throws IOException {
        
        reqPool.runTask(() -> {
            String xmlRecord = cacheService.getXmlRecord(recid);
            if (xmlRecord == null) {
                System.out.println("Không tìm thấy record");
                return; // thoát khỏi Runnable, không trả về giá trị
            }
            System.out.println(xmlRecord);
            if (xmlRecord != null && !xmlRecord.isEmpty() && xmlRecord.contains("a")) {
                counter.incrementAndGet(); // tăng counter mỗi khi tìm thấy record
            }
        });

        return ResponseEntity.ok("OK");
        // return ResponseEntity.ok(objectMapper.readTree(xmlRecord));
    }

    @GetMapping(value = "/cache/getCounter")
    public ResponseEntity<String> getCounter() throws IOException {
        return ResponseEntity.ok(String.valueOf(counter.get()));
    }

    @GetMapping("/getRecord1")
    public ResponseEntity<String> getRecord1() {
        // Get all fields
        var result = t24DBService.getRecordByRecid("test1", "a");
        return ResponseEntity.ok("Record fetched: " + 
            result.getXmlrecord().get("store_info").get("location").get("city").asText()
        );
    }

    @GetMapping("/getRecord2")
    public ResponseEntity<String> getRecord2() {
        // Get selected fields
        var result = t24DBService.getRecordByRecid("test1", List.of("1", "3"), "b");
        return ResponseEntity.ok("Record fetched: " + 
            result.getXmlrecord().get("3").toString() // Array
        );
    }

    @GetMapping("/getRecord3")
    public ResponseEntity<String> getRecord3() {
        // Get selected fields
        var result = t24DBService.getRecordByRecidV2("V_FBNK_COLLATERAL_RIGHT"
            , Set.of("LIMIT.REFERENCE", "COLLATERAL.CODE")
            , "COR23080THP3X"
        );
        return ResponseEntity.ok("Record fetched: " + 
            result.getXmlrecord().get("LIMIT.REFERENCE").toString() // Array
        );
    }


    // Crash the application if no pool is used
    // @GetMapping("/getRecord4_noPool")
    // public ResponseEntity<String> getRecord4() throws IOException, InterruptedException {
    //     HttpRequest request = HttpRequest.newBuilder()
    //             .uri(URI.create("http://localhost:8080/test-api/test/info"))
    //             .GET()
    //             .build();
    //     HttpResponse<String> response = HttpClient.newHttpClient()
    //             .send(request, HttpResponse.BodyHandlers.ofString());

    //     return ResponseEntity.status(response.statusCode()).body(response.body());
    // }

    @GetMapping("/getRecord4_withPool")
    public ResponseEntity<String> getRecord4WithPool() throws IOException, InterruptedException, ExecutionException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/test-api/test/info"))
                .GET()
                .build();
        HttpResponse<String> response = reqPool.submitTask(
                () -> httpClient.send(request, HttpResponse.BodyHandlers.ofString()))
                .get();

        return ResponseEntity.status(response.statusCode()).body(response.body());
    }
}
