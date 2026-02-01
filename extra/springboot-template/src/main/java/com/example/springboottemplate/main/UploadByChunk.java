package com.example.springboottemplate.main;

import com.example.springboottemplate.WonderUtils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

public class UploadByChunk {
    
    public static void main(String[] args) {
        // if (args.length < 1) {
        //     System.out.println("Usage: java UploadByChunk <file-path>");
        //     System.out.println("Example: java UploadByChunk C:\\Users\\Admin\\Desktop\\VID_20250920_185055.mp4");
        //     return;
        // }
        
        String filePath = "C:\\Users\\Admin\\Desktop\\VID_20250920_185055.mp4";
        String tempFolder = "E:\\CODING\\Temp";
        String apiUrl = "http://localhost:8080/test-api/api/upload/{filename}";
        int chunkSizeInMB = 9;
        
        try {
            System.out.println("Starting upload process...");
            System.out.println("File: " + filePath);
            System.out.println("Chunk size: " + chunkSizeInMB + " MB");
            System.out.println("Temp folder: " + tempFolder);
            System.out.println();
            
            // Get file info
            File sourceFile = new File(filePath);
            if (!sourceFile.exists()) {
                System.err.println("Error: File does not exist: " + filePath);
                return;
            }
            
            double fileSizeMB = WonderUtils.getFileSizeInMB(filePath);
            System.out.println("File size: " + fileSizeMB + " MB");
            System.out.println();
            
            // Split file into chunks
            System.out.println("Step 1: Splitting file into " + chunkSizeInMB + "MB chunks...");
            List<String> partFiles = WonderUtils.splitFile(filePath, chunkSizeInMB, tempFolder);
            System.out.println("Created " + partFiles.size() + " chunks");
            System.out.println();
            
            // Upload each chunk
            System.out.println("Step 2: Uploading chunks...");
            int successCount = 0;
            int failCount = 0;
            
            // Create HttpClient
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();
            
            for (int i = 0; i < partFiles.size(); i++) {
                String partFilePath = partFiles.get(i);
                File partFile = new File(partFilePath);
                String partFileName = partFile.getName();
                
                System.out.println("Uploading chunk " + (i + 1) + "/" + partFiles.size() + ": " + partFileName);
                
                try {
                    // Read file content as bytes
                    byte[] fileBytes = Files.readAllBytes(partFile.toPath());
                    
                    // Build upload URL
                    String uploadUrl = apiUrl.replace("{filename}", partFileName);
                    
                    // Create HTTP request
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(uploadUrl))
                            .header("Content-Type", "application/octet-stream")
                            .timeout(Duration.ofMinutes(5))
                            .POST(HttpRequest.BodyPublishers.ofByteArray(fileBytes))
                            .build();
                    
                    // Send request
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    
                    System.out.println("  Status: " + response.statusCode());
                    System.out.println("  Response: " + response.body());
                    
                    if (response.statusCode() >= 200 && response.statusCode() < 300) {
                        successCount++;
                    } else {
                        failCount++;
                    }
                    
                } catch (Exception e) {
                    System.err.println("  Error uploading chunk: " + e.getMessage());
                    failCount++;
                }
                
                System.out.println();
            }
            
            // Summary
            System.out.println("=".repeat(60));
            System.out.println("Upload Summary:");
            System.out.println("Total chunks: " + partFiles.size());
            System.out.println("Success: " + successCount);
            System.out.println("Failed: " + failCount);
            System.out.println();
            
            // Clean up temp files
            System.out.print("Do you want to delete temporary chunk files? (y/n): ");
            String answer = System.console() != null ? System.console().readLine() : "y";
            
            if (answer.trim().equalsIgnoreCase("y")) {
                System.out.println("Cleaning up temporary files...");
                for (String partFilePath : partFiles) {
                    try {
                        WonderUtils.deleteFile(partFilePath);
                        System.out.println("  Deleted: " + new File(partFilePath).getName());
                    } catch (IOException e) {
                        System.err.println("  Failed to delete: " + partFilePath);
                    }
                }
                System.out.println("Cleanup completed.");
            }
            
            System.out.println();
            System.out.println("Upload process completed!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
