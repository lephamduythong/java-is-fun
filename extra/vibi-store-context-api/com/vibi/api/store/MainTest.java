package com.vibi.api.store;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainTest {
    
    /**
     * Send a request and wait for response with polling
     * 
     * @param requestStoreApi  The request store API instance
     * @param responseStoreApi The response store API instance
     * @param id              The ID to use for request/response
     * @param value           The value to send in the request
     * @return The response value, or null if not received
     * @throws Exception If any error occurs
     */
    public static String sendAndWait(
            IVibiStoreAPI requestStoreApi,
            IVibiStoreAPI responseStoreApi,
            String id,
            String value) throws Exception {
        
        // Write request to requestStoreApi
        System.out.println("Sending request with ID: " + id + ", Value: " + value);
        requestStoreApi.write(id, value);
        
        // Poll responseStoreApi for the response using 2 threads
        System.out.println("Waiting for response...");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> pollingFuture = new CompletableFuture<>();
        
        // Thread 1: Poll for data
        CompletableFuture.runAsync(() -> {
            try {
                while (!Thread.currentThread().isInterrupted() && !pollingFuture.isDone()) {
                    String response = responseStoreApi.read(id);
                    if (response != null) {
                        System.out.println("✓ Received response: " + response);
                        pollingFuture.complete(response);
                        requestStoreApi.delete(id);
                        responseStoreApi.delete(id);
                        break;
                    }
                    Thread.sleep(250);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                pollingFuture.completeExceptionally(e);
            }
        }, executor);
        
        // Thread 2: Timeout tracker
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(120000); // 2 minutes
                if (!pollingFuture.isDone()) {
                    System.err.println("❌ Timeout: No response received after 2 minutes. Shutting down...");
                    pollingFuture.completeExceptionally(new TimeoutException("Response timeout after 2 minutes"));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, executor);
        
        try {
            // Wait for either polling to complete or timeout
            String response = pollingFuture.get();
            return response;
        } catch (Exception e) {
            if (e.getCause() instanceof TimeoutException) {
                System.exit(1);
            }
            throw e;
        } finally {
            // Shutdown executor and interrupt threads
            executor.shutdownNow();
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static VibiStoreContextAPI storeContextApi = null;
    
    public static void main(String[] args)  {
        
        try {
            System.out.println("=== Starting VibiStoreContextAPI Test ===\n");
            storeContextApi = VibiStoreContextAPI.getInstance("N:\\DB\\CLGT.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            while (true) {
                System.out.println("Writing a random data every 3 seconds...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                try {
                    if (storeContextApi == null) {
                        storeContextApi = VibiStoreContextAPI.getInstance("N:\\DB\\CLGT.db");
                    }
                    storeContextApi.write(UUID.randomUUID().toString(), UUID.randomUUID().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown(); // Luôn nhớ đóng executor khi xong việc


            // IVibiStoreAPI requestStoreApi = VibiRequestStoreAPI.getInstance();
            // IVibiStoreAPI responseStoreApi = VibiResponseStoreAPI.getInstance();
            
            // // Test sendAndWait method
            // String testId = "id3";
            // String testValue = "GGWP2";
            // String response = sendAndWait(requestStoreApi, responseStoreApi, testId, testValue);

            
            
            // System.out.println("\n=== Test completed successfully! ===");
            // System.out.println("Final response: " + response);

        
            //     System.out.println("   ✓ Database created at: " + api.getDatabasePath());
            
        //     // 2. Check WAL mode
        //     System.out.println("\n2. Checking WAL mode...");
        //     Connection conn = api.getConnection();
        //     Statement stmt = conn.createStatement();
        //     ResultSet rs = stmt.executeQuery("PRAGMA journal_mode;");
        //     if (rs.next()) {
        //         String journalMode = rs.getString(1);
        //         System.out.println("   ✓ Journal mode: " + journalMode);
        //     }
        //     rs.close();
            
        //     // 3. Create test table
        //     System.out.println("\n3. Creating test table...");
        //     stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
        //                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        //                 "name TEXT NOT NULL, " +
        //                 "email TEXT UNIQUE)");
        //     System.out.println("   ✓ Table 'users' has been created");
            
        //     // 4. Insert data
        //     System.out.println("\n4. Inserting data...");
        //     stmt.execute("INSERT INTO users (name, email) VALUES ('Nguyen Van A', 'vana@example.com')");
        //     stmt.execute("INSERT INTO users (name, email) VALUES ('Tran Thi B', 'thib@example.com')");
        //     System.out.println("   ✓ Added 2 records");
            
        //     // 5. Read data
        //     System.out.println("\n5. Reading data from database...");
        //     rs = stmt.executeQuery("SELECT * FROM users");
        //     while (rs.next()) {
        //         int id = rs.getInt("id");
        //         String name = rs.getString("name");
        //         String email = rs.getString("email");
        //         System.out.println("   - ID: " + id + ", Name: " + name + ", Email: " + email);
        //     }
        //     rs.close();
            
        //     // 6. Test singleton pattern
        //     System.out.println("\n6. Testing singleton pattern...");
        //     VibiStoreContextAPI api2 = VibiStoreContextAPI.getInstance("test_database.sql");
        //     if (api == api2) {
        //         System.out.println("   ✓ Singleton works correctly (same instance)");
        //     } else {
        //         System.out.println("   ✗ Singleton does not work (different instance)");
        //     }
            
        //     // 7. Close connection
        //     System.out.println("\n7. Closing connection...");
        //     stmt.close();
        //     api.closeConnection();
        //     System.out.println("   ✓ Connection closed");
            
        //     System.out.println("\n=== Test completed successfully! ===");
            
        
    }
}
