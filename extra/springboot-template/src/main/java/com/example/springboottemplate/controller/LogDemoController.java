package com.example.springboottemplate.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/logging")
public class LogDemoController {

    private static final Logger logger = LogManager.getLogger(LogDemoController.class);

    @GetMapping("/test-all-levels")
    public Map<String, String> testAllLogLevels() {
        logger.trace("TRACE: Most detailed information, typically used for debugging");
        logger.debug("DEBUG: Detailed information for debugging purposes");
        logger.info("INFO: General informational messages");
        logger.warn("WARN: Warning messages for potentially harmful situations");
        logger.error("ERROR: Error events that might still allow the app to continue");
        logger.fatal("FATAL: Very severe error events that might cause the app to abort");

        Map<String, String> response = new HashMap<>();
        response.put("message", "All log levels have been tested");
        response.put("logLocation", "Check logs/application.log and logs/error.log");
        return response;
    }

    @PostMapping("/user-action")
    public Map<String, String> logUserAction(@RequestBody Map<String, Object> payload) {
        String action = (String) payload.getOrDefault("action", "unknown");
        String userId = (String) payload.getOrDefault("userId", "anonymous");

        logger.info("User action logged - UserId: {}, Action: {}", userId, action);
        logger.debug("Full payload: {}", payload);

        Map<String, String> response = new HashMap<>();
        response.put("status", "logged");
        response.put("message", "User action has been logged successfully");
        return response;
    }

    @GetMapping("/simulate-error")
    public Map<String, String> simulateError() {
        logger.info("Simulating an error scenario");
        
        try {
            int result = 10 / 0; // This will throw ArithmeticException
        } catch (ArithmeticException e) {
            logger.error("ArithmeticException caught: Division by zero", e);
            
            Map<String, String> response = new HashMap<>();
            response.put("error", "An error was simulated and logged");
            response.put("checkLogs", "logs/error.log");
            return response;
        }
        
        return null;
    }

    @GetMapping("/performance-log")
    public Map<String, Object> performanceLog() {
        long startTime = System.currentTimeMillis();
        logger.info("Performance monitoring started");

        // Simulate some processing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logger.info("Performance monitoring completed in {} ms", duration);
        
        if (duration > 50) {
            logger.warn("Operation took longer than expected: {} ms", duration);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("duration", duration);
        response.put("unit", "milliseconds");
        response.put("status", "completed");
        return response;
    }
}
