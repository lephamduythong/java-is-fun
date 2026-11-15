package com.example.springboottemplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
        Thread.sleep(500);

        Map<String, Object> data = new HashMap<>();
        data.put("id", UUID.randomUUID().toString());
        data.put("name", UUID.randomUUID().toString());
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
    public ResponseEntity<String> getCacheRecord2(@PathVariable String recid) {
        
        reqPool.runTask(() -> {
            // String xmlRecord = cacheService.getXmlRecord(recid);
            // if (xmlRecord == null) {
            //     System.out.println("Không tìm thấy record");
            //     return; // thoát khỏi Runnable, không trả về giá trị
            // }
            // System.out.println(xmlRecord);
            // if (xmlRecord != null && !xmlRecord.isEmpty() && xmlRecord.contains("a")) {
            //     counter.incrementAndGet(); // tăng counter mỗi khi tìm thấy record
            // }

            try {
                Thread.sleep(200);
                throw new RuntimeException("Test exception");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            System.out.println("Done fetching data");

        });

        return ResponseEntity.ok("OK");
        // return ResponseEntity.ok(objectMapper.readTree(xmlRecord));
    }

    @GetMapping(value = "/cache/restartThreadPool", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> restartThreadPool() {
        
        reqPool.restart();

        return ResponseEntity.ok("restartThreadPool");
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

    @GetMapping("/getHttp1")
    public CompletableFuture<ResponseEntity<String>> getHttp1() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/test-api/test/info"))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        
        // Vẫn không ổn vì call local tới api vẫn bị Undertow giới hạn trong jboss
        var result = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> ResponseEntity.status(response.statusCode()).body(response.body()))
                .exceptionally(this::handleHttpError);

        System.out.println("PASS");

        return result;
    }

    @GetMapping("/getHttp1_better")
    public ResponseEntity<Map<String, Object>> getHttp1_better() throws InterruptedException {
        
        // Use direct method
        var result = getInfo();

        System.out.println("PASS");

        return result;
    }

    // Test with parallel requests to increase speed get data in one single request
    @GetMapping("/getHttp2")
    public CompletableFuture<ResponseEntity<String>> getHttp2() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/test-api/test/info"))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        List<CompletableFuture<HttpResponse<String>>> requests = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            requests.add(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()));
        }

        return CompletableFuture.allOf(requests.toArray(new CompletableFuture<?>[0]))
                .thenApply(ignored -> {
                    StringBuilder results = new StringBuilder();
                    for (CompletableFuture<HttpResponse<String>> requestFuture : requests) {
                        if (results.length() > 0) {
                            results.append('\n');
                        }
                        results.append(requestFuture.join().body());
                    }
                    System.out.println("DONE getHttp2");
                    return ResponseEntity.ok(results.toString());
                })
                .exceptionally(this::handleHttpError);
    }

    private ResponseEntity<String> handleHttpError(Throwable error) {
        Throwable cause = error instanceof CompletionException ? error.getCause() : error;
        if (cause instanceof HttpTimeoutException) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Request timeout");
        }
        throw new CompletionException(cause);
    }

    @GetMapping("/getHttp3")
    public ResponseEntity<String> getHttp3() throws InterruptedException, ExecutionException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/test-api/test/info"))
                .GET()
                .build();

        String results = reqPool.submitTask(() -> {
            ExecutorService smallPool = Executors.newFixedThreadPool(5);
            try {
                List<Future<HttpResponse<String>>> requests = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    requests.add(smallPool.submit(
                            () -> httpClient.send(request, HttpResponse.BodyHandlers.ofString())));
                }

                StringBuilder responseBodies = new StringBuilder();
                ExecutionException firstFailure = null;
                for (Future<HttpResponse<String>> requestFuture : requests) {
                    try {
                        if (responseBodies.length() > 0) {
                            responseBodies.append('\n');
                        }
                        responseBodies.append(requestFuture.get().body());
                    } catch (ExecutionException e) {
                        if (firstFailure == null) {
                            firstFailure = e;
                        }
                    }
                }
                if (firstFailure != null) {
                    throw firstFailure;
                }

                return responseBodies.toString();
            } finally {
                smallPool.shutdown();
            }
        }).get();

        return ResponseEntity.ok(results);
    }
}
