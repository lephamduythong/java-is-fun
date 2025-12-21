package com.example.springboottemplate.config;

import java.io.IOException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.example.springboottemplate.Utils;
import com.example.springboottemplate.Constants;

@Component
public class CamelConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Configure REST DSL
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true");

        // Define REST endpoints
        rest("/users")
            .description("User REST service")
            .get("/{id}")
                .description("Get user by ID")
                .to("direct:getUser")
            .get()
                .description("Get all users")
                .to("direct:getAllUsers")
            .post()
                .description("Create new user")
                .type(User.class)
                .to("direct:createUser");

        rest("/images")
            .description("Image service")
            .get("/cat")
                .description("Get cat image")
                .produces("image/jpeg")
                .to("direct:getCatImage");

        rest("/download")
            .description("File download service")
            .get("/{filename}")
                .description("Download file from E:\\CODING\\Download folder")
                .to("direct:downloadFile");

        rest("/upload")
            .description("File upload service")
            .post("/{filename}")
                .description("Upload file to E:\\CODING\\Upload folder")
                .consumes("multipart/form-data,application/octet-stream")
                .bindingMode(RestBindingMode.off)
                .to("direct:uploadFile");

        // Implement route logic
        from("direct:getUser")
            .routeId("getUserRoute")
            .log("Getting user with ID: ${header.id}")
            .process(exchange -> {
                String userId = exchange.getIn().getHeader("id", String.class);
                User user = new User(userId, "User " + userId, "user" + userId + "@example.com");
                exchange.getIn().setBody(user);
            });

        from("direct:getAllUsers")
            .routeId("getAllUsersRoute")
            .log("Getting all users")
            .process(exchange -> {
                java.util.List<User> users = java.util.Arrays.asList(
                    new User("1", "John Doe", "john@example.com"),
                    new User("2", "Jane Smith", "jane@example.com"),
                    new User("3", "Bob Johnson", "bob@example.com")
                );
                exchange.getIn().setBody(users);
            });

        from("direct:createUser")
            .routeId("createUserRoute")
            .log("Creating user: ${body}")
            .process(exchange -> {
                User user = exchange.getIn().getBody(User.class);
                // Simulate user creation
                user.setId(String.valueOf(System.currentTimeMillis()));
                exchange.getIn().setBody(user);
            })
            .setHeader("Content-Type", constant("application/json"));
        
        Utils.logTextToFile(Constants.LOG_FILE_PATH, "Camel route started");

        from("direct:getCatImage")
            .routeId("getCatImageRoute")
            .log("Reading cat image from classpath")
            .process(exchange -> {
                try {
                    // Read file from classpath
                    byte[] imageBytes = getClass().getClassLoader()
                        .getResourceAsStream("static/img/cat.jpg")
                        .readAllBytes();
                    exchange.getIn().setBody(imageBytes);
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "Cat image read successfully");
                } catch (Exception e) {
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "Error reading cat image: " + e.getMessage());
                    throw e;
                }
            })
            .setHeader("Content-Type", constant("image/jpeg"))
            .setHeader("Content-Disposition", constant("inline; filename=cat.jpg"));

        from("direct:downloadFile")
            .routeId("downloadFileRoute")
            .log("Downloading file: ${header.filename}")
            .process(exchange -> {
                try {
                    String filename = exchange.getIn().getHeader("filename", String.class);
                    String filePath = "E:\\CODING\\Download\\" + filename;
                    
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "Attempting to download file: " + filePath);
                    
                    java.io.File file = new java.io.File(filePath);
                    if (!file.exists() || !file.isFile()) {
                        Utils.logTextToFile(Constants.LOG_FILE_PATH, "File not found: " + filePath);
                        exchange.getIn().setHeader(org.apache.camel.Exchange.HTTP_RESPONSE_CODE, 404);
                        exchange.getIn().setBody("File not found: " + filename);
                        return;
                    }
                    
                    // Read file bytes
                    byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
                    exchange.getIn().setBody(fileBytes);
                    
                    // Determine content type
                    String contentType = java.nio.file.Files.probeContentType(file.toPath());
                    if (contentType == null) {
                        contentType = "application/octet-stream";
                    }
                    
                    exchange.getIn().setHeader("Content-Type", contentType);
                    exchange.getIn().setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                    exchange.getIn().setHeader(org.apache.camel.Exchange.HTTP_RESPONSE_CODE, 200);
                    
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "File downloaded successfully: " + filename);
                } catch (Exception e) {
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "Error downloading file: " + e.getMessage());
                    exchange.getIn().setHeader(org.apache.camel.Exchange.HTTP_RESPONSE_CODE, 500);
                    exchange.getIn().setBody("Error downloading file: " + e.getMessage());
                }
            });

        from("direct:uploadFile")
            .routeId("uploadFileRoute")
            .log("Uploading file: ${header.filename}")
            .process(exchange -> {
                try {
                    String filename = exchange.getIn().getHeader("filename", String.class);
                    String uploadFolder = "E:\\CODING\\Upload\\";
                    
                    // Create upload directory if it doesn't exist
                    java.io.File uploadDir = new java.io.File(uploadFolder);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                        Utils.logTextToFile(Constants.LOG_FILE_PATH, "Created upload directory: " + uploadFolder);
                    }
                    
                    String filePath = uploadFolder + filename;
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "Attempting to upload file: " + filePath);
                    
                    // Get file content from request body
                    byte[] fileBytes = exchange.getIn().getBody(byte[].class);
                    
                    if (fileBytes == null || fileBytes.length == 0) {
                        Utils.logTextToFile(Constants.LOG_FILE_PATH, "No file content received");
                        exchange.getIn().setHeader(org.apache.camel.Exchange.HTTP_RESPONSE_CODE, 400);
                        exchange.getIn().setBody("{\"success\": false, \"message\": \"No file content received\"}");
                        exchange.getIn().setHeader("Content-Type", "application/json");
                        return;
                    }
                    
                    // Write file to disk
                    java.nio.file.Files.write(
                        java.nio.file.Paths.get(filePath),
                        fileBytes,
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
                    );
                    
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "File uploaded successfully: " + filename + " (" + fileBytes.length + " bytes)");
                    
                    exchange.getIn().setHeader(org.apache.camel.Exchange.HTTP_RESPONSE_CODE, 200);
                    exchange.getIn().setBody("{\"success\": true, \"message\": \"File uploaded successfully\", \"filename\": \"" + filename + "\", \"size\": " + fileBytes.length + "}");
                    exchange.getIn().setHeader("Content-Type", "application/json");
                } catch (Exception e) {
                    Utils.logTextToFile(Constants.LOG_FILE_PATH, "Error uploading file: " + e.getMessage());
                    exchange.getIn().setHeader(org.apache.camel.Exchange.HTTP_RESPONSE_CODE, 500);
                    exchange.getIn().setBody("{\"success\": false, \"message\": \"Error uploading file: " + e.getMessage() + "\"}");
                    exchange.getIn().setHeader("Content-Type", "application/json");
                }
            });
    }

    // Inner class for User model
    public static class User {
        private String id;
        private String name;
        private String email;

        public User() {}

        public User(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
