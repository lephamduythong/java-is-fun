package com.example.springboottemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WonderUtils {
    
    /**
     * Transform string by replacing characters according to specific rules:
     * 1. , -> ?
     * 2. _ -> '_'
     * 3. / -> ^
     * 
     * @param input the input string to transform
     * @return the transformed string
     */
    public static String escapseOFS(String input) {
        if (input == null) {
            return null;
        }
        
        return input.replace(",", "?")
                   .replace("_", "'_'")
                   .replace("/", "^");
    }
    
    /**
     * Append text to a file at the specified path
     * Creates parent directories and file if they don't exist
     * 
     * @param path the file path where to append the text
     * @param text the text content to append
     * @throws IOException if an I/O error occurs
     */
    public static void logTextToFile(String path, String text) throws IOException {
        if (path == null || text == null) {
            throw new IllegalArgumentException("Path and text cannot be null");
        }
        
        File file = new File(path);
        
        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }
        
        try (FileWriter writer = new FileWriter(file, true)) {
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("\n" + dateTime + " ----------------\n");
            writer.write(text);
            writer.flush();
        }
        
    }

    /**
     * Append text to a file at the specified path
     * Creates parent directories and file if they don't exist
     * 
     * @param path the file path where to append the text
     * @param text the text content to append
     * @throws IOException if an I/O error occurs
     */
    public static void appendTextToFile(String path, String text) throws IOException {
        if (path == null || text == null) {
            throw new IllegalArgumentException("Path and text cannot be null");
        }
        
        File file = new File(path);
        
        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }
        
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(text);
            writer.flush();
        }
        
    }

    /**
     * Prepend text to a file at the specified path
     * Creates parent directories and file if they don't exist
     * 
     * @param path the file path where to prepend the text
     * @param text the text content to prepend
     * @throws IOException if an I/O error occurs
     */
    public static void prependTextToFile(String path, String text) throws IOException {
        if (path == null || text == null) {
            throw new IllegalArgumentException("Path and text cannot be null");
        }
        
        File file = new File(path);
        
        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }
        
        String existingContent = "";
        if (file.exists()) {
            existingContent = new String(Files.readAllBytes(file.toPath()));
        }
        
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(text + existingContent);
            writer.flush();
        }
    }
    
    /**
     * Write text to a file at the specified path, replacing existing content
     * Creates parent directories and file if they don't exist
     * 
     * @param path the file path where to write the text
     * @param text the text content to write
     * @throws IOException if an I/O error occurs
     */
    public static void writeTextToFile(String path, String text) throws IOException {
        if (path == null || text == null) {
            throw new IllegalArgumentException("Path and text cannot be null");
        }
        
        File file = new File(path);
        
        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }
        
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(text);
            writer.flush();
        }
    }
    
    /**
     * Read a file and convert it to Base64 string
     * 
     * @param path the file path to read
     * @return Base64 encoded string of the file content
     * @throws IOException if an I/O error occurs
     */
    public static String fileToBase64(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + path);
        }
        
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
    
    /**
     * Decode Base64 string and write it to a file
     * Creates parent directories and file if they don't exist
     * 
     * @param base64String the Base64 encoded string to decode
     * @param path the file path where to write the decoded content
     * @throws IOException if an I/O error occurs
     */
    public static void base64ToFile(String base64String, String path) throws IOException {
        if (base64String == null || path == null) {
            throw new IllegalArgumentException("Base64 string and path cannot be null");
        }
        
        File file = new File(path);
        
        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }
        
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        Files.write(file.toPath(), decodedBytes);
    }
    
    /**
     * Make HTTP request to the specified URL
     * 
     * @param url the URL to make request to
     * @param method HTTP method (GET, POST, PUT, DELETE)
     * @param requestBody request body content (can be null for GET/DELETE)
     * @param headers request headers (can be null)
     * @return response body as string
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the request is interrupted
     */
    public static String httpRequest(String url, String method, String requestBody, Map<String, String> headers) 
            throws IOException, InterruptedException {
        if (url == null || method == null) {
            throw new IllegalArgumentException("URL and method cannot be null");
        }
        
                HttpClient client = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
            
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30));
            
            // Set headers if provided
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.header(header.getKey(), header.getValue());
                }
            }
            
            // Set method and body
            switch (method.toUpperCase()) {
                case "GET":
                    requestBuilder.GET();
                    break;
                case "POST":
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody != null ? requestBody : ""));
                    break;
                case "PUT":
                    requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(requestBody != null ? requestBody : ""));
                    break;
                case "DELETE":
                    requestBuilder.DELETE();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            }
            
            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();
    }
    
    /**
     * Make simple GET request to the specified URL
     * 
     * @param url the URL to make GET request to
     * @return response body as string
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the request is interrupted
     */
    public static String httpGet(String url) throws IOException, InterruptedException {
        return httpRequest(url, "GET", null, null);
    }
    
    /**
     * Calculate MD5 checksum of a file
     * 
     * @param path the file path to calculate MD5 checksum
     * @return MD5 checksum as hexadecimal string
     * @throws IOException if an I/O error occurs
     * @throws NoSuchAlgorithmException if MD5 algorithm is not available
     */
    public static String calculateMD5(String path) throws IOException, NoSuchAlgorithmException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + path);
        }
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }
        
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
    
    /**
     * Split a file into multiple parts based on size in MB and save to specified folder
     * 
     * @param filePath the path of the file to split
     * @param sizeInMB the maximum size of each part in MB
     * @param outputFolder the folder where to save the split parts
     * @return list of generated part file paths
     * @throws IOException if an I/O error occurs
     */
    public static List<String> splitFile(String filePath, int sizeInMB, String outputFolder) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        if (outputFolder == null) {
            throw new IllegalArgumentException("Output folder cannot be null");
        }
        if (sizeInMB <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        
        File sourceFile = new File(filePath);
        if (!sourceFile.exists()) {
            throw new IOException("Source file does not exist: " + filePath);
        }
        
        // Create output folder if it doesn't exist
        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Failed to create output directory: " + outputFolder);
            }
        }
        
        List<String> partFiles = new ArrayList<>();
        long bytesPerPart = (long) sizeInMB * 1024 * 1024; // Convert MB to bytes
        byte[] buffer = new byte[8192];
        String baseFileName = sourceFile.getName();
        
        try (FileInputStream fis = new FileInputStream(sourceFile)) {
            int partNumber = 1;
            long bytesWritten = 0;
            FileOutputStream currentPartStream = null;
            
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                // Create new part file if needed
                if (currentPartStream == null || bytesWritten >= bytesPerPart) {
                    if (currentPartStream != null) {
                        currentPartStream.close();
                    }
                    
                    String partFileName = outputFolder + File.separator + baseFileName + ".part" + String.format("%03d", partNumber);
                    partFiles.add(partFileName);
                    currentPartStream = new FileOutputStream(partFileName);
                    bytesWritten = 0;
                    partNumber++;
                }
                
                currentPartStream.write(buffer, 0, bytesRead);
                bytesWritten += bytesRead;
            }
            
            if (currentPartStream != null) {
                currentPartStream.close();
            }
        }
        
        return partFiles;
    }
    
    /**
     * Merge multiple files into a single file
     * 
     * @param partFilePaths list of file paths to merge (in order)
     * @param outputFilePath the path of the merged output file
     * @throws IOException if an I/O error occurs
     */
    public static void mergeFiles(List<String> partFilePaths, String outputFilePath) throws IOException {
        if (partFilePaths == null || partFilePaths.isEmpty()) {
            throw new IllegalArgumentException("Part file paths list cannot be null or empty");
        }
        if (outputFilePath == null) {
            throw new IllegalArgumentException("Output file path cannot be null");
        }
        
        File outputFile = new File(outputFilePath);
        
        // Create parent directories if they don't exist
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }
        
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[8192];
            
            for (String partFilePath : partFilePaths) {
                File partFile = new File(partFilePath);
                if (!partFile.exists()) {
                    throw new IOException("Part file does not exist: " + partFilePath);
                }
                
                try (FileInputStream fis = new FileInputStream(partFile)) {
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }
    
    /**
     * Delete a file at the specified path
     * 
     * @param path the file path to delete
     * @return true if the file was successfully deleted, false if the file didn't exist
     * @throws IOException if an I/O error occurs during deletion
     */
    public static boolean deleteFile(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        
        if (!file.delete()) {
            throw new IOException("Failed to delete file: " + path);
        }
        
        return true;
    }
    
    /**
     * Delete a folder and all its contents recursively
     * 
     * @param path the folder path to delete
     * @return true if the folder was successfully deleted, false if the folder didn't exist
     * @throws IOException if an I/O error occurs during deletion
     */
    public static boolean deleteFolder(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        File folder = new File(path);
        if (!folder.exists()) {
            return false;
        }
        
        if (!folder.isDirectory()) {
            throw new IOException("Path is not a directory: " + path);
        }
        
        return deleteFolderRecursive(folder);
    }
    
    /**
     * Helper method to recursively delete folder contents
     * 
     * @param folder the folder to delete
     * @return true if successful
     * @throws IOException if an I/O error occurs during deletion
     */
    private static boolean deleteFolderRecursive(File folder) throws IOException {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolderRecursive(file);
                } else {
                    if (!file.delete()) {
                        throw new IOException("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        }
        
        if (!folder.delete()) {
            throw new IOException("Failed to delete folder: " + folder.getAbsolutePath());
        }
        
        return true;
    }
    
    /**
     * Generate a string in format "<date>-<time>-<input>"
     * Date format: YYYYMMDD (e.g., 20250512)
     * Time format: HHMMSS (e.g., 141230)
     * 
     * @param input the string to append after date and time
     * @return generated timestamp string with input
     */
    public static String generateTimestampId(String input) {
        LocalDateTime now = LocalDateTime.now();
        
        String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String time = now.format(DateTimeFormatter.ofPattern("HHmmss"));
        
        return date + "-" + time + "-" + (input != null ? input : "");
    }

    /**
     * Generate a random alphanumeric hash string with specified length
     * Characters include: 0-9 and A-Z
     * 
     * @param inputNum the length of the hash string to generate
     * @return randomly generated alphanumeric string
     * @throws IllegalArgumentException if inputNum is less than or equal to 0
     */
    public static String generateRandomHash(int inputNum) {
        if (inputNum <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder hash = new StringBuilder(inputNum);
        java.util.Random random = new java.util.Random();
        
        for (int i = 0; i < inputNum; i++) {
            int index = random.nextInt(characters.length());
            hash.append(characters.charAt(index));
        }
        
        return hash.toString();
    }

    /**
     * Read entire text content from a file at the specified path
     * 
     * @param path the file path to read from
     * @return the entire text content of the file as string
     * @throws IOException if an I/O error occurs
     */
    public static String readTextFromFile(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + path);
        }
        
        return new String(Files.readAllBytes(file.toPath()));
    }

    /**
     * Get the size of a file in megabytes (MB)
     * 
     * @param path the file path to get size of
     * @return the size of the file in MB (rounded to 2 decimal places)
     * @throws IOException if an I/O error occurs or file doesn't exist
     */
    public static double getFileSizeInMB(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + path);
        }
        
        if (file.isDirectory()) {
            throw new IOException("Path is a directory, not a file: " + path);
        }
        
        long sizeInBytes = file.length();
        double sizeInMB = sizeInBytes / (1024.0 * 1024.0);
        
        return Math.round(sizeInMB * 100.0) / 100.0; // Round to 2 decimal places
    }

    /**
     * Extract string from input using regex pattern at specified group index
     * 
     * @param input the input string to match against
     * @param regexPattern the regex pattern to use for matching
     * @param groupIndex the group index to extract (0 for entire match, 1+ for capturing groups)
     * @return the extracted string from the specified group, or null if no match found
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public static String extractStrFromRegex(String input, String regexPattern, int groupIndex) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        if (regexPattern == null || regexPattern.isEmpty()) {
            throw new IllegalArgumentException("Regex pattern cannot be null or empty");
        }
        if (groupIndex < 0) {
            throw new IllegalArgumentException("Group index must be non-negative");
        }
        
        try {
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(input);
            
            if (matcher.find()) {
                if (groupIndex > matcher.groupCount()) {
                    throw new IllegalArgumentException("Group index " + groupIndex + " is greater than available groups (" + matcher.groupCount() + ")");
                }
                return matcher.group(groupIndex);
            }
            
            return null; // No match found
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid regex pattern: " + regexPattern + " - " + e.getMessage());
        }
    }
}