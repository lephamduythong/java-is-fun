package com.example.netty.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Main application để chạy Apache Camel với Netty HTTP
 * Ví dụ standalone sử dụng Camel route với Netty HTTP component
 * 
 * Chạy class này để start Camel HTTP server trên port 9090
 */
public class CamelNettyMain {
    
    private static final int PORT = 9090;
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Apache Camel + Netty HTTP Server ===");
        System.out.println("Starting Camel context...\n");
        
        // Tạo CamelContext
        CamelContext camelContext = new DefaultCamelContext();
        
        // Add shutdown hook để graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down Camel context...");
            try {
                camelContext.stop();
                System.out.println("Camel context stopped successfully.");
            } catch (Exception e) {
                System.err.println("Error stopping Camel context: " + e.getMessage());
            }
        }));
        
        try {
            // Thêm routes
            camelContext.addRoutes(new CamelHttpRoute());
            
            // Start CamelContext
            camelContext.start();
            
            System.out.println("✓ Camel Context started successfully!");
            System.out.println("✓ HTTP Server listening on port " + PORT);
            System.out.println("\n=== Available Endpoints ===\n");
            
            printEndpoints();
            
            System.out.println("\n=== Server Information ===");
            System.out.println("Camel Version: " + camelContext.getVersion());
            System.out.println("Routes Count: " + camelContext.getRoutes().size());
            System.out.println("Status: " + camelContext.getStatus());
            System.out.println("\nPress Ctrl+C to stop the server...\n");
            
            // Keep the application running
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("Error starting Camel context: " + e.getMessage());
            e.printStackTrace();
            camelContext.stop();
            System.exit(1);
        }
    }
    
    private static void printEndpoints() {
        System.out.println("1. Simple Greeting:");
        System.out.println("   GET  http://localhost:" + PORT + "/camel/hello");
        System.out.println("   Returns a simple JSON greeting message\n");
        
        System.out.println("2. Server Information:");
        System.out.println("   GET  http://localhost:" + PORT + "/camel/info");
        System.out.println("   Returns detailed server and request information\n");
        
        System.out.println("3. Echo Service:");
        System.out.println("   POST http://localhost:" + PORT + "/camel/echo");
        System.out.println("   Body: {\"message\":\"Hello\"}");
        System.out.println("   Echoes back the JSON payload with timestamp\n");
        
        System.out.println("4. Path Parameter Transform:");
        System.out.println("   GET  http://localhost:" + PORT + "/camel/transform/YourName");
        System.out.println("   Transforms and returns information about the path parameter\n");
        
        System.out.println("5. Delayed Response:");
        System.out.println("   GET  http://localhost:" + PORT + "/camel/delay/3");
        System.out.println("   Returns response after specified delay (1-10 seconds)\n");
        
        System.out.println("6. JSON Validation:");
        System.out.println("   POST http://localhost:" + PORT + "/camel/validate");
        System.out.println("   Body: {\"email\":\"test@example.com\",\"name\":\"John\"}");
        System.out.println("   Validates required fields in JSON payload\n");
        
        System.out.println("7. Route Statistics:");
        System.out.println("   GET  http://localhost:" + PORT + "/camel/stats");
        System.out.println("   Returns Camel runtime statistics\n");
    }
    
    /**
     * Test endpoints using curl commands (optional helper method)
     */
    @SuppressWarnings("unused")
    private static void printCurlExamples() {
        System.out.println("\n=== Curl Test Commands ===\n");
        System.out.println("# Test hello endpoint");
        System.out.println("curl http://localhost:" + PORT + "/camel/hello\n");
        
        System.out.println("# Test info endpoint");
        System.out.println("curl http://localhost:" + PORT + "/camel/info\n");
        
        System.out.println("# Test echo endpoint");
        System.out.println("curl -X POST http://localhost:" + PORT + "/camel/echo \\");
        System.out.println("  -H \"Content-Type: application/json\" \\");
        System.out.println("  -d '{\"message\":\"Hello from Camel\"}'");
        System.out.println();
        
        System.out.println("# Test transform endpoint");
        System.out.println("curl http://localhost:" + PORT + "/camel/transform/Alice\n");
        
        System.out.println("# Test delay endpoint");
        System.out.println("curl http://localhost:" + PORT + "/camel/delay/2\n");
        
        System.out.println("# Test validate endpoint");
        System.out.println("curl -X POST http://localhost:" + PORT + "/camel/validate \\");
        System.out.println("  -H \"Content-Type: application/json\" \\");
        System.out.println("  -d '{\"email\":\"user@example.com\",\"name\":\"John Doe\"}'");
        System.out.println();
        
        System.out.println("# Test stats endpoint");
        System.out.println("curl http://localhost:" + PORT + "/camel/stats\n");
    }
}
