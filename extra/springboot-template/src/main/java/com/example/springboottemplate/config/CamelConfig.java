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
