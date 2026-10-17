package com.example.springboottemplate;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
     * Write a list of strings to a file quickly
     * Creates the file if it doesn't exist, and truncates existing content
     * 
     * @param path the file path where to write the data
     * @param data the list of strings to write
     * @throws Exception if an I/O error occurs
     */
    public static void writeFastStrList(String path, List<String> data) throws Exception {
        Files.write(Paths.get(path), data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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
     * @param path         the file path where to write the decoded content
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
     * @param url         the URL to make request to
     * @param method      HTTP method (GET, POST, PUT, DELETE)
     * @param requestBody request body content (can be null for GET/DELETE)
     * @param headers     request headers (can be null)
     * @return response body as string
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the request is interrupted
     */
    public static HashMap<String, String> httpRequest(String url, String method, String requestBody, Map<String, String> headers)
            throws IOException, InterruptedException {
        if (url == null || method == null) {
            throw new IllegalArgumentException("URL and method cannot be null");
        }

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(300));

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

        // Serialize headers thành 1 chuỗi, mỗi header 1 dòng dạng "key: value1,value2"
        String headersStr = response.headers().map().entrySet().stream()
                .map(e -> e.getKey() + ": " + String.join(",", e.getValue()))
                .collect(Collectors.joining("\n"));

        HashMap<String, String> result = new HashMap<>();
        result.put("body", response.body());
        result.put("headers", headersStr);
        result.put("statusCode", String.valueOf(response.statusCode())); // tiện thể thêm luôn, bỏ dòng này nếu không cần

        return result;
    }

    /**
     * Make parallel HTTP requests to the specified URLs
     * 
     * @param urls        map of keys and URLs to make requests to
     * @param method      HTTP method (GET, POST, PUT, DELETE)
     * @param requestBody request body content (can be null for GET/DELETE)
     * @param headers     request headers (can be null)
     * @return map of keys and response bodies
     * @throws InterruptedException if the request is interrupted
     * @throws ExecutionException   if the request execution fails
     */
    public static Map<String, String> parallelHttpRequest(
            Map<String, String> urls,
            String method,
            String requestBody,
            Map<String, String> headers)
            throws InterruptedException, ExecutionException {

        int totalRequests = urls.size();
        int cpuCores = Runtime.getRuntime().availableProcessors();
        int threadCount = Math.min(totalRequests, (cpuCores * (1 + 4)));

        System.out.println("Total requests: " + totalRequests);
        System.out.println("CPU cores: " + cpuCores);
        System.out.println("Thread count: " + threadCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        Map<String, Future<String>> futures = new HashMap<>();
        for (Map.Entry<String, String> entry : urls.entrySet()) {
            final String key = entry.getKey();
            final String url = entry.getValue();
            Future<String> future = executor.submit(() -> {
                try {
                    String body = httpRequest(url, method, requestBody, headers).get("body");
                    System.out.println("Done request to " + key + " | URL: " + url);
                    return body;
                } catch (Exception e) {
                    return String.format("Error: %s", e.getMessage());
                }
            });
            futures.put(key, future);
        }

        executor.shutdown();

        Map<String, String> results = new HashMap<>();
        for (Map.Entry<String, Future<String>> entry : futures.entrySet()) {
            String result = entry.getValue().get();
            results.put(entry.getKey(), result);
        }

        return results;
    }

    /**
     * Make simple GET request to the specified URL
     * 
     * @param url the URL to make GET request to
     * @return response body as string
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the request is interrupted
     */
    public static String httpGet(String url) throws IOException, InterruptedException {
        return httpRequest(url, "GET", null, null).get("body");
    }

    /**
     * Calculate MD5 checksum of a file
     * 
     * @param path the file path to calculate MD5 checksum
     * @return MD5 checksum as hexadecimal string
     * @throws IOException              if an I/O error occurs
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
     * Split a file into multiple parts based on size in MB and save to specified
     * folder
     * 
     * @param filePath     the path of the file to split
     * @param sizeInMB     the maximum size of each part in MB
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

                    String partFileName = outputFolder + File.separator + baseFileName + ".part"
                            + String.format("%03d", partNumber);
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
     * @param partFilePaths  list of file paths to merge (in order)
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
     * List all files in a directory sorted by name (ascending)
     * 
     * @param folderPath the path of the directory to list files from
     * @return list of file paths sorted by file name
     * @throws IOException if an I/O error occurs or path is not a directory
     */
    public static List<String> listFilesSortedByName(String folderPath) throws IOException {
        if (folderPath == null) {
            throw new IllegalArgumentException("Folder path cannot be null");
        }

        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new IOException("Directory does not exist: " + folderPath);
        }
        if (!folder.isDirectory()) {
            throw new IOException("Path is not a directory: " + folderPath);
        }

        File[] files = folder.listFiles(File::isFile);
        if (files == null) {
            return new ArrayList<>();
        }

        java.util.Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        List<String> result = new ArrayList<>();
        for (File file : files) {
            result.add(file.getName());
        }
        return result;
    }

    /**
     * List all files in a directory sorted by last modified date (newest first)
     * 
     * @param folderPath the path of the directory to list files from
     * @return list of file paths sorted by last modified date descending
     * @throws IOException if an I/O error occurs or path is not a directory
     */
    public static List<String> listFilesSortedByLastModified(String folderPath) throws IOException {
        if (folderPath == null) {
            throw new IllegalArgumentException("Folder path cannot be null");
        }

        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new IOException("Directory does not exist: " + folderPath);
        }
        if (!folder.isDirectory()) {
            throw new IOException("Path is not a directory: " + folderPath);
        }

        File[] files = folder.listFiles(File::isFile);
        if (files == null) {
            return new ArrayList<>();
        }

        java.util.Arrays.sort(files, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));

        List<String> result = new ArrayList<>();
        for (File file : files) {
            result.add(file.getName());
        }
        return result;
    }

    /**
     * Delete a file at the specified path
     * 
     * @param path the file path to delete
     * @return true if the file was successfully deleted, false if the file didn't
     *         exist
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
     * @return true if the folder was successfully deleted, false if the folder
     *         didn't exist
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
     * @param input        the input string to match against
     * @param regexPattern the regex pattern to use for matching
     * @param groupIndex   the group index to extract (0 for entire match, 1+ for
     *                     capturing groups)
     * @return the extracted string from the specified group, or null if no match
     *         found
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
                    throw new IllegalArgumentException("Group index " + groupIndex
                            + " is greater than available groups (" + matcher.groupCount() + ")");
                }
                return matcher.group(groupIndex);
            }

            return null; // No match found

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid regex pattern: " + regexPattern + " - " + e.getMessage());
        }
    }

    /**
     * Execute a SQL query on the provided database connection and return results as a list of maps.
     * Each map in the list represents a row of the result set, with column names as keys and their corresponding values as map values.
     * @param connection the database connection to use for executing the query
     * @param sql the SQL query to execute
     * @param alias the list of column names to retrieve from the result set
     * @return a list of maps representing the query results, where each map corresponds to a row with column names as keys
     * @throws SQLException if a database access error occurs or the SQL query is invalid
     * @throws IllegalArgumentException if any of the input parameters are null or invalid
     */
    public static List<HashMap<String, String>> queryDB(Connection connection, String sql)
            throws SQLException {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL cannot be null or empty");
        }

        final var result = new ArrayList<HashMap<String, String>>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Lấy tên cột/alias 1 lần duy nhất, tránh gọi lại metadata mỗi row
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                // getColumnLabel trả về alias (sau AS) nếu có, không thì trả về tên cột gốc
                columnNames[i] = meta.getColumnLabel(i + 1);
            }

            while (rs.next()) {
                final var item = new HashMap<String, String>();
                for (int i = 0; i < columnCount; i++) {
                    item.put(columnNames[i], rs.getString(i + 1));
                }
                result.add(item);
            }
        }

        return result;
    }
    
    public static List<HashMap<String, String>> queryDB(Connection connection, String sql, Object... params)
            throws SQLException {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL cannot be null or empty");
        }

        final var result = new ArrayList<HashMap<String, String>>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set parameters
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                String[] columnNames = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    columnNames[i] = meta.getColumnLabel(i + 1);
                }

                while (rs.next()) {
                    final var item = new HashMap<String, String>();
                    for (int i = 0; i < columnCount; i++) {
                        item.put(columnNames[i], rs.getString(i + 1));
                    }
                    result.add(item);
                }
            }
        }

        return result;
    }

    /**
     * Update a database table with the provided data list.
     *
     * @param conn      the database connection
     * @param tableName the name of the table to update
     * @param dataList  the list of data to update
     * @param idColumn  the column used as the identifier
     * @param mode      the update mode (UPDATE, DELETE)
     * @throws SQLException if a database access error occurs
     */
    public static void updateTableDB(
            Connection conn,
            String tableName,
            List<HashMap<String, String>> dataList,
            String idColumn,
            String mode) throws SQLException {
 
        // Validation
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("dataList cannot be null or empty");
        }
        if (!"UPDATE".equals(mode) && !"DELETE".equals(mode)) {
            throw new IllegalArgumentException("Mode must be one of UPDATE or DELETE: " + mode);
        }
        boolean hasIdColumn = dataList.stream().anyMatch(r -> r.containsKey(idColumn));
        if (!hasIdColumn) {
            throw new IllegalArgumentException("There is a row containing no idColumn info: " + idColumn);
        }
 
        // Processing
        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        System.out.println("START TO PROCESS");
        long count = 0;
        try {
            for (HashMap<String, String> row : dataList) {
                String idValue = row.get(idColumn);
                boolean isNew = (idValue == null || idValue.trim().isEmpty());
 
                // ── DELETE MODE ──────────────────────────────────────────────
                if ("DELETE".equals(mode)) {
                    if (isNew) {
                        // Row has no id, skip
                        continue;
                    }
 
                    String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
 
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, idValue);
                        int affected = ps.executeUpdate();
                        // System.out.println("[DELETE] affected=" + affected + " | id=" + idValue);
                    }
 
                    count++;
                    if (count % 1000 == 0) {
                        System.out.println("Processed: " + count);
                    }
                    continue;
                }
 
                // ── UPDATE MODE ──────────────────────────────────────────────
                if (isNew) {
                    // Row has no id, skip (no INSERT/UPSERT fallback anymore)
                    continue;
                }
 
                List<String> updateCols = new ArrayList<>();
                for (String col : row.keySet()) {
                    if (!col.equals(idColumn)) {
                        updateCols.add(col);
                    }
                }
                if (updateCols.isEmpty()) {
                    // Row only has idColumn, nothing to update
                    continue;
                }
 
                StringBuilder sql = new StringBuilder("UPDATE ")
                        .append(tableName).append(" SET ");
                for (int i = 0; i < updateCols.size(); i++) {
                    sql.append(updateCols.get(i)).append(" = ?");
                    if (i < updateCols.size() - 1)
                        sql.append(", ");
                }
                sql.append(" WHERE ").append(idColumn).append(" = ?");
 
                try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                    for (int i = 0; i < updateCols.size(); i++) {
                        ps.setString(i + 1, row.get(updateCols.get(i)));
                    }
                    ps.setString(updateCols.size() + 1, idValue);
                    int affected = ps.executeUpdate();
                    // System.out.println("[UPDATE] affected=" + affected + " | " + row);
                }
 
                count++;
                if (count % 1000 == 0) {
                    System.out.println("Processed: " + count);
                }
            }
            conn.commit();
 
            System.out.println("ALL PROCESSED ITEMS: " + dataList.size());
            System.out.println("[TRANSACTION] COMMITED.");
        } catch (SQLException e) {
            conn.rollback();
            System.err.println("[TRANSACTION] ROLLBACK, error: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(prevAutoCommit);
        }
    }
    
    /**
     * Update a database table with the provided data list.
     *
     * @param conn      the database connection
     * @param tableName the name of the table to update
     * @param dataList  the list of data to update
     * @param idColumn  the column used as the identifier
     * @param mode      the update mode (UPDATE, DELETE)
     * @throws SQLException if a database access error occurs
     */
    public static void updateTableBatchDB(
            Connection conn,
            String tableName,
            List<HashMap<String, String>> dataList,
            String idColumn,
            String mode) throws SQLException {
        updateTableBatchDB(conn, tableName, dataList, idColumn, mode, 500);
    }

    /**
     * Update/delete a database table with the provided data list, sử dụng
     * JDBC batch ({@code addBatch}/{@code executeBatch}) để tăng tốc thay vì
     * chạy {@code executeUpdate()} riêng lẻ cho từng row.
     *
     * Với mode UPDATE, các row có tập cột cần cập nhật khác nhau sẽ được
     * gom nhóm theo "signature" của tập cột đó (vì mỗi PreparedStatement chỉ
     * chạy được đúng 1 câu SQL cố định), mỗi nhóm dùng 1 PreparedStatement
     * riêng để batch. Mode DELETE luôn cùng 1 câu SQL nên batch chung hết.
     *
     * @param conn      the database connection
     * @param tableName the name of the table to update
     * @param dataList  the list of data to update
     * @param idColumn  the column used as the identifier
     * @param mode      the update mode (UPDATE, DELETE)
     * @param batchSize số row tối đa dồn lại trước khi gọi executeBatch()
     * @throws SQLException if a database access error occurs
     */
    public static void updateTableBatchDB(
            Connection conn,
            String tableName,
            List<HashMap<String, String>> dataList,
            String idColumn,
            String mode,
            int batchSize) throws SQLException {

        // Validation
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("dataList cannot be null or empty");
        }
        if (!"UPDATE".equals(mode) && !"DELETE".equals(mode)) {
            throw new IllegalArgumentException("Mode must be one of UPDATE or DELETE: " + mode);
        }
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be at least 1");
        }
        boolean hasIdColumn = dataList.stream().anyMatch(r -> r.containsKey(idColumn));
        if (!hasIdColumn) {
            throw new IllegalArgumentException("There is a row containing no idColumn info: " + idColumn);
        }

        // Processing
        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        System.out.println("START TO PROCESS (batch mode, batchSize=" + batchSize + ")");

        long count = 0;
        long skipped = 0;

        // key = signature tập cột cần update, chỉ dùng cho mode UPDATE
        Map<String, UpdateBatchGroup> groups = new LinkedHashMap<>();

        try {
            if ("DELETE".equals(mode)) {
                String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    int pending = 0;
                    for (HashMap<String, String> row : dataList) {
                        String idValue = row.get(idColumn);
                        if (idValue == null || idValue.trim().isEmpty()) {
                            skipped++;
                            continue;
                        }

                        ps.setString(1, idValue);
                        ps.addBatch();
                        pending++;
                        count++;

                        if (pending >= batchSize) {
                            ps.executeBatch();
                            pending = 0;
                            System.out.println("Processed: " + count);
                        }
                    }
                    if (pending > 0) {
                        ps.executeBatch();
                    }
                }
            } else {
                // ── UPDATE MODE ──────────────────────────────────────────────
                for (HashMap<String, String> row : dataList) {
                    String idValue = row.get(idColumn);
                    if (idValue == null || idValue.trim().isEmpty()) {
                        // Row has no id, skip (no INSERT/UPSERT fallback anymore)
                        skipped++;
                        continue;
                    }

                    List<String> updateCols = new ArrayList<>();
                    for (String col : row.keySet()) {
                        if (!col.equals(idColumn)) {
                            updateCols.add(col);
                        }
                    }
                    if (updateCols.isEmpty()) {
                        // Row only has idColumn, nothing to update
                        skipped++;
                        continue;
                    }

                    // Signature để nhóm các row có cùng tập cột cần update lại với nhau
                    String signature = String.join("\u0001", updateCols);
                    UpdateBatchGroup group = groups.get(signature);
                    if (group == null) {
                        StringBuilder sql = new StringBuilder("UPDATE ")
                                .append(tableName).append(" SET ");
                        for (int i = 0; i < updateCols.size(); i++) {
                            sql.append(updateCols.get(i)).append(" = ?");
                            if (i < updateCols.size() - 1)
                                sql.append(", ");
                        }
                        sql.append(" WHERE ").append(idColumn).append(" = ?");

                        group = new UpdateBatchGroup(conn.prepareStatement(sql.toString()), updateCols);
                        groups.put(signature, group);
                    }

                    for (int i = 0; i < updateCols.size(); i++) {
                        group.ps.setString(i + 1, row.get(updateCols.get(i)));
                    }
                    group.ps.setString(updateCols.size() + 1, idValue);
                    group.ps.addBatch();
                    group.pending++;
                    count++;

                    if (group.pending >= batchSize) {
                        group.ps.executeBatch();
                        group.pending = 0;
                        System.out.println("Processed: " + count);
                    }
                }

                // Flush các batch còn dở của từng nhóm
                for (UpdateBatchGroup group : groups.values()) {
                    if (group.pending > 0) {
                        group.ps.executeBatch();
                    }
                }
            }

            conn.commit();

            System.out.println("ALL PROCESSED ITEMS: " + count + " (skipped=" + skipped + ")");
            System.out.println("[TRANSACTION] COMMITED.");
        } catch (SQLException e) {
            conn.rollback();
            System.err.println("[TRANSACTION] ROLLBACK, error: " + e.getMessage());
            throw e;
        } finally {
            for (UpdateBatchGroup group : groups.values()) {
                try {
                    group.ps.close();
                } catch (SQLException ignore) {
                    // best-effort cleanup
                }
            }
            conn.setAutoCommit(prevAutoCommit);
        }
    }

    /**
     * Gom 1 PreparedStatement + số lượng batch đang chờ cho 1 tập cột UPDATE cụ thể.
     */
    private static class UpdateBatchGroup {
        final PreparedStatement ps;
        final List<String> columns;
        int pending = 0;

        UpdateBatchGroup(PreparedStatement ps, List<String> columns) {
            this.ps = ps;
            this.columns = columns;
        }
    }

    /**
     * Insert a list of rows into a database table using JDBC batch execution for
     * high performance. All rows must share the same set of columns (derived from
     * the first non-null row). Rows whose column set differs from the reference are
     * skipped with a warning. The entire batch is committed as a single transaction;
     * on any error the transaction is rolled back.
     *
     * @param conn      the database connection
     * @param tableName the name of the target table
     * @param dataList  the list of rows to insert; each row is a map of column name
     *                  to value
     * @param batchSize number of rows per {@code executeBatch()} call (e.g. 500)
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if {@code dataList} is null/empty or
     *                                  {@code batchSize} is less than 1
     */
    public static void insertBatchTableDB(
            Connection conn,
            String tableName,
            List<HashMap<String, String>> dataList,
            int batchSize) throws SQLException {

        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("tableName cannot be null or empty");
        }
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("dataList cannot be null or empty");
        }
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be at least 1");
        }

        // Derive column order from the first non-null row
        HashMap<String, String> firstRow = null;
        for (HashMap<String, String> r : dataList) {
            if (r != null && !r.isEmpty()) {
                firstRow = r;
                break;
            }
        }
        if (firstRow == null) {
            throw new IllegalArgumentException("dataList contains no valid (non-null/non-empty) rows");
        }
        List<String> columns = new ArrayList<>(firstRow.keySet());
        Set<String> columnSet = new LinkedHashSet<>(columns);

        // Build INSERT SQL once
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            sql.append(columns.get(i));
            placeholders.append("?");
            if (i < columns.size() - 1) {
                sql.append(", ");
                placeholders.append(", ");
            }
        }
        sql.append(") VALUES (").append(placeholders).append(")");

        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        System.out.println("[insertBatchTableDB] START | table=" + tableName
                + " | rows=" + dataList.size()
                + " | batchSize=" + batchSize);

        long totalInserted = 0;
        long skipped = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int pendingInBatch = 0;

            for (HashMap<String, String> row : dataList) {
                if (row == null) {
                    skipped++;
                    continue;
                }
                // Skip rows whose column set doesn't match the reference
                if (!row.keySet().equals(columnSet)) {
                    System.out.println("[SKIP] Row column set differs from reference, skipping: " + row);
                    skipped++;
                    continue;
                }

                for (int i = 0; i < columns.size(); i++) {
                    ps.setString(i + 1, row.get(columns.get(i)));
                }
                ps.addBatch();
                pendingInBatch++;

                if (pendingInBatch >= batchSize) {
                    ps.executeBatch();
                    totalInserted += pendingInBatch;
                    pendingInBatch = 0;
                    System.out.println("[insertBatchTableDB] Inserted so far: " + totalInserted);
                }
            }

            // Flush remaining rows
            if (pendingInBatch > 0) {
                ps.executeBatch();
                totalInserted += pendingInBatch;
            }

            conn.commit();
            System.out.println("[insertBatchTableDB] COMMITTED | inserted=" + totalInserted + " | skipped=" + skipped);

        } catch (SQLException e) {
            conn.rollback();
            System.err.println("[insertBatchTableDB] ROLLBACK | error: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(prevAutoCommit);
        }
    }

    /**
     * Delete all rows from the specified table using TRUNCATE TABLE.
     * Falls back to DELETE FROM if TRUNCATE fails (e.g. table has FK constraints).
     *
     * @param conn      the database connection
     * @param tableName the name of the table to truncate
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if conn or tableName is null/empty
     */
    public static void truncateTableDB(Connection conn, String tableName) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("tableName cannot be null or empty");
        }

        // Try TRUNCATE first (faster, no undo log per-row on most DBs)
        try (PreparedStatement ps = conn.prepareStatement("TRUNCATE TABLE " + tableName)) {
            ps.executeUpdate();
            System.out.println("[truncateTableDB] TRUNCATE OK | table=" + tableName);
        } catch (SQLException e) {
            // Fallback to DELETE FROM (works even with FK constraints)
            System.out.println("[truncateTableDB] TRUNCATE failed (" + e.getMessage()
                    + "), falling back to DELETE FROM");
            boolean prevAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tableName)) {
                int deleted = ps.executeUpdate();
                conn.commit();
                System.out.println("[truncateTableDB] DELETE OK | table=" + tableName + " | rows deleted=" + deleted);
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("[truncateTableDB] ROLLBACK | error: " + ex.getMessage());
                throw ex;
            } finally {
                conn.setAutoCommit(prevAutoCommit);
            }
        }
    }

    /**
     * Execute an arbitrary DDL or DML SQL statement (e.g. DROP TABLE, ALTER TABLE,
     * CREATE INDEX, GRANT ...). Use this for one-off statements that do not return
     * a result set. For SELECT queries use queryTableDB; for bulk inserts use
     * insertBatchTableDB.
     *
     * @param conn the database connection
     * @param sql  the SQL statement to execute
     * @throws SQLException             if a database access error occurs
     * @throws IllegalArgumentException if conn or sql is null/empty
     */
    public static void executeDB(Connection conn, String sql) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("sql cannot be null or empty");
        }

        System.out.println("[executeDB] SQL=" + sql.trim());
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql.trim());
            System.out.println("[executeDB] OK");
        } catch (SQLException e) {
            System.err.println("[executeDB] FAILED | error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Export table data to CSV file.
     *
     * @param table list of rows; each row is a map of field name to value
     * @param path  output CSV file path
     * @throws IOException if an I/O error occurs
     */
    public static void exportTableToCsv(List<HashMap<String, String>> table, String path) throws IOException {
        if (table == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }

        File file = new File(path);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories: " + parentDir.getAbsolutePath());
            }
        }

        Set<String> headers = new LinkedHashSet<>();
        for (HashMap<String, String> row : table) {
            if (row != null) {
                headers.addAll(row.keySet());
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            if (headers.isEmpty()) {
                return;
            }

            List<String> headerList = new ArrayList<>(headers);

            for (int i = 0; i < headerList.size(); i++) {
                writer.write(escapeCsvValue(headerList.get(i)));
                if (i < headerList.size() - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            for (HashMap<String, String> row : table) {
                for (int i = 0; i < headerList.size(); i++) {
                    String value = row == null ? null : row.get(headerList.get(i));
                    writer.write(escapeCsvValue(value));
                    if (i < headerList.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        }
    }

    /**
     * Import CSV file to table data.
     *
     * @param path input CSV file path
     * @return list of rows; each row is a map of field name to value
     * @throws IOException if an I/O error occurs
     */
    public static List<HashMap<String, String>> importCsvToTable(String path) throws IOException {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }

        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + path);
        }
        if (file.isDirectory()) {
            throw new IOException("Path is a directory, not a file: " + path);
        }

        String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        List<List<String>> rows = parseCsvContent(content);

        List<HashMap<String, String>> table = new ArrayList<>();
        if (rows.isEmpty()) {
            return table;
        }

        List<String> headers = new ArrayList<>(rows.get(0));
        if (!headers.isEmpty() && headers.get(0) != null && headers.get(0).startsWith("\uFEFF")) {
            headers.set(0, headers.get(0).substring(1));
        }

        for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) {
            List<String> values = rows.get(rowIndex);
            HashMap<String, String> rowMap = new HashMap<>();

            for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                String header = headers.get(colIndex);
                String value = colIndex < values.size() ? values.get(colIndex) : "";
                rowMap.put(header, value);
            }

            table.add(rowMap);
        }

        return table;
    }

    private static List<List<String>> parseCsvContent(String content) {
        List<List<String>> rows = new ArrayList<>();
        if (content == null || content.isEmpty()) {
            return rows;
        }

        List<String> currentRow = new ArrayList<>();
        StringBuilder currentCell = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < content.length(); i++) {
            char ch = content.charAt(i);

            if (inQuotes) {
                if (ch == '"') {
                    if (i + 1 < content.length() && content.charAt(i + 1) == '"') {
                        currentCell.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    currentCell.append(ch);
                }
                continue;
            }

            if (ch == '"') {
                inQuotes = true;
                continue;
            }
            if (ch == ',') {
                currentRow.add(currentCell.toString());
                currentCell.setLength(0);
                continue;
            }
            if (ch == '\n') {
                currentRow.add(currentCell.toString());
                rows.add(currentRow);
                currentRow = new ArrayList<>();
                currentCell.setLength(0);
                continue;
            }
            if (ch == '\r') {
                continue;
            }

            currentCell.append(ch);
        }

        boolean hasRemainingData = currentCell.length() > 0 || !currentRow.isEmpty() || content.endsWith(",");
        if (hasRemainingData) {
            currentRow.add(currentCell.toString());
            rows.add(currentRow);
        }

        return rows;
    }

    private static String escapeCsvValue(String value) {
        if (value == null) {
            return "\"\"";
        }
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    /**
     * Convert table data to JSON array string.
     *
     * @param table list of rows; each row is a map of field name to value
     * @return JSON array string containing row objects
     */
    public static String convertTableToJsonArray(List<HashMap<String, String>> table) {
        if (table == null) {
            throw new IllegalArgumentException("Table cannot be null");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int rowIndex = 0; rowIndex < table.size(); rowIndex++) {
            HashMap<String, String> row = table.get(rowIndex);
            if (row == null) {
                sb.append("null");
            } else {
                sb.append("{");
                int pairIndex = 0;
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    if (pairIndex > 0) {
                        sb.append(",");
                    }

                    String key = entry.getKey();
                    String value = entry.getValue();

                    sb.append("\"").append(escapeJsonString(key == null ? "" : key)).append("\":");
                    if (value == null) {
                        sb.append("null");
                    } else {
                        sb.append("\"").append(escapeJsonString(value)).append("\"");
                    }
                    pairIndex++;
                }
                sb.append("}");
            }

            if (rowIndex < table.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static String escapeJsonString(String value) {
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    if (ch < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) ch));
                    } else {
                        escaped.append(ch);
                    }
                    break;
            }
        }
        return escaped.toString();
    }

    /**
     * Parse a JSON-like string value into a {@code List<String>}.
     *
     * Supported inputs:
     * - JSON-style array text, e.g. {@code ["A", "B", null]}.
     * - Single scalar text (non-array), e.g. {@code "A"} or {@code A}.
     *
     * Parsing behavior:
     * - Returns an empty list when input is {@code null}, blank, or literal
     * {@code "null"}.
     * - If the input is wrapped with {@code [ ]}, it splits items by comma.
     * - Trims whitespace around each item.
     * - Converts item literal {@code null} (case-insensitive) to Java {@code null}.
     * - Removes surrounding single or double quotes from each item when present.
     * - For non-array input, returns a single-item list after quote trimming.
     *
     * Note: This is a lightweight parser and does not fully support all JSON edge
     * cases
     * (for example, commas inside quoted values).
     *
     * @param str raw JSON-like array string or scalar value
     * @return parsed list of string values (may contain {@code null} elements)
     */
    public static List<String> extractJsonArrayStrToList(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.trim().isEmpty() || "null".equalsIgnoreCase(str.trim())) {
            return result;
        }
        String cleaned = str.trim();
        if (cleaned.startsWith("[") && cleaned.endsWith("]")) {
            String content = cleaned.substring(1, cleaned.length() - 1).trim();
            if (content.isEmpty()) {
                return result;
            }

            // Smart split that respects nested arrays and quotes
            List<String> elements = smartSplit(content);

            for (String element : elements) {
                String trimmedElement = element.trim();
                if ("null".equalsIgnoreCase(trimmedElement)) {
                    result.add(null);
                } else {
                    if (trimmedElement.startsWith("\"") && trimmedElement.endsWith("\"")
                            && trimmedElement.length() >= 2) {
                        trimmedElement = trimmedElement.substring(1, trimmedElement.length() - 1);
                    } else if (trimmedElement.startsWith("'") && trimmedElement.endsWith("'")
                            && trimmedElement.length() >= 2) {
                        trimmedElement = trimmedElement.substring(1, trimmedElement.length() - 1);
                    }
                    result.add(trimmedElement);
                }
            }
        } else {
            if (cleaned.startsWith("\"") && cleaned.endsWith("\"") && cleaned.length() >= 2) {
                cleaned = cleaned.substring(1, cleaned.length() - 1);
            } else if (cleaned.startsWith("'") && cleaned.endsWith("'") && cleaned.length() >= 2) {
                cleaned = cleaned.substring(1, cleaned.length() - 1);
            }
            result.add(cleaned);
        }
        return result;
    }

    private static List<String> smartSplit(String str) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int bracketDepth = 0;
        boolean inDoubleQuote = false;
        boolean inSingleQuote = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                current.append(c);
            } else if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                current.append(c);
            } else if (c == '[' && !inDoubleQuote && !inSingleQuote) {
                bracketDepth++;
                current.append(c);
            } else if (c == ']' && !inDoubleQuote && !inSingleQuote) {
                bracketDepth--;
                current.append(c);
            } else if (c == ',' && bracketDepth == 0 && !inDoubleQuote && !inSingleQuote) {
                // This is a separator comma at the top level
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        // Add the last element
        if (current.length() > 0) {
            result.add(current.toString());
        }

        return result;
    }
    
    /**
     * Functional interface đại diện cho một tác vụ sẽ được thực thi
     * trong lúc đang giữ {@link FileLock}.
     */
    @FunctionalInterface
    public interface LockedTask {
        void execute(FileLock lock) throws Exception;
    }
 
    public static void withSharedLock(Path path, LockedTask task, long retryIntervalMs, int maxRetries) {
        try (FileChannel channel = FileChannel.open(path,
                StandardOpenOption.CREATE,
                StandardOpenOption.READ)) {
 
            System.out.println("Attempting to acquire shared lock...");
            FileLock lock = channel.tryLock(0, Long.MAX_VALUE, true);
 
            if (lock == null) {
                System.out.println("Unable to acquire lock. File is held by an exclusive lock — retrying...");
                int attempts = 0;
                while (lock == null) {
                    if (maxRetries != -1 && attempts >= maxRetries) {
                        System.err.println("Failed to acquire lock after " + maxRetries + " attempt(s).");
                        return;
                    }
                    attempts++;
                    String maxRetriesLabel = maxRetries == -1 ? "∞" : String.valueOf(maxRetries);
                    System.out.printf("Attempt %d/%s — retrying in %dms...%n", attempts, maxRetriesLabel, retryIntervalMs);
                    Thread.sleep(retryIntervalMs);
                    lock = channel.tryLock(0, Long.MAX_VALUE, true);
                }
                System.out.println("Shared lock acquired successfully after " + attempts + " attempt(s).");
            } else {
                System.out.println("Shared lock acquired successfully!");
            }
 
            try {
                task.execute(lock);
            } finally {
                if (lock != null && lock.isValid()) {
                    lock.release();
                }
            }
 
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
 
    public static void withSharedLock(Path path, LockedTask task) {
        withSharedLock(path, task, 500, -1);
    }
    
    /**
     * Tạo chuỗi placeholder dạng (?, ?, ?) cho câu lệnh SQL IN
     * @param size Số lượng phần tử
     * @return Chuỗi chứa các dấu hỏi chấm cách nhau bằng dấu phẩy
     */
    public static String createSqlQueryPlaceholdersForIN(int size) {
        if (size <= 0) {
            return "()"; // Tránh lỗi cú pháp SQL nếu danh sách rỗng
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < size; i++) {
            sb.append("?");
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
    
    public static Connection getJNDIConnection(String jndiName) throws NamingException, SQLException {
        var ds = (DataSource) (new InitialContext()).lookup(jndiName);
        return ds.getConnection();
    }
    
    public static boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }
    
    public static String safeStr(String value) {
        return value == null ? "" : value;
    }
    
    public HashMap<String, String> httpRequestOld(String url, String method, String requestBody, Map<String, String> headers)
            throws IOException {
        if (url == null || method == null) {
            throw new IllegalArgumentException("URL and method cannot be null");
        }

        String httpMethod = method.toUpperCase();

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        try {
            connection.setConnectTimeout(30_000);   // connect timeout: 30s
            connection.setReadTimeout(300_000);     // read timeout: 300s
            connection.setRequestMethod(httpMethod);

            // Set headers if provided
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            // Set method and body
            switch (httpMethod) {
                case "GET":
                    connection.setDoOutput(false);
                    break;
                case "POST":
                case "PUT":
                    connection.setDoOutput(true);
                    writeBody(connection, requestBody);
                    break;
                case "DELETE":
                    boolean hasBody = requestBody != null && !requestBody.isEmpty();
                    connection.setDoOutput(hasBody);
                    if (hasBody) {
                        writeBody(connection, requestBody);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
            }

            int statusCode;
            try {
                statusCode = connection.getResponseCode();
            } catch (IOException e) {
                // Một số trường hợp (vd: server đóng kết nối bất thường) getResponseCode() ném IOException
                // thay vì trả về mã lỗi. Log lại để không mất thông tin khi debug production.
                throw e;
            }

            // Đọc body: dùng InputStream nếu status OK, ErrorStream nếu lỗi (4xx/5xx)
            InputStream is = (statusCode >= 200 && statusCode < 400)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            String responseBody = readStream(is);

            // Serialize headers thành 1 chuỗi, mỗi header 1 dòng dạng "key: value1,value2"
            String headersStr = connection.getHeaderFields().entrySet().stream()
                    .filter(e -> e.getKey() != null) // dòng đầu tiên (status line) có key = null, bỏ qua
                    .map(e -> e.getKey() + ": " + String.join(",", e.getValue()))
                    .collect(Collectors.joining("\n"));

            HashMap<String, String> result = new HashMap<>();
            result.put("body", responseBody);
            result.put("headers", headersStr);
            result.put("statusCode", String.valueOf(statusCode));

            return result;
        } finally {
            // Đảm bảo connection luôn được đóng, kể cả khi có exception ở bất kỳ bước nào phía trên
            connection.disconnect();
        }
    }

    private static void writeBody(HttpURLConnection connection, String requestBody) throws IOException {
        byte[] bodyBytes = (requestBody != null ? requestBody : "").getBytes(StandardCharsets.UTF_8);
        connection.setFixedLengthStreamingMode(bodyBytes.length);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(bodyBytes);
        }
    }

    private static String readStream(InputStream is) throws IOException {
        if (is == null) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
    
    public static void writeObjectToFile(Object obj, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filePath)))) {
            oos.writeObject(obj);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T readObjectFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filePath)))) {
            return (T) ois.readObject();
        }
    }
}