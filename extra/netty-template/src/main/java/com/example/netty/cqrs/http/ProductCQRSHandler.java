package com.example.netty.cqrs.http;

import com.example.netty.cqrs.command.CreateProductCommand;
import com.example.netty.cqrs.command.DeleteProductCommand;
import com.example.netty.cqrs.command.UpdateProductCommand;
import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.mediator.CQRSMediator;
import com.example.netty.cqrs.query.GetAllProductsQuery;
import com.example.netty.cqrs.query.GetProductByIdQuery;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTTP Handler for CQRS Product operations
 * Routes:
 * - GET /api/products - Get all products
 * - GET /api/products/{id} - Get product by ID
 * - POST /api/products - Create new product
 * - PUT /api/products/{id} - Update product
 * - DELETE /api/products/{id} - Delete product
 */
public class ProductCQRSHandler {
    
    private final CQRSMediator mediator;
    private final ObjectMapper objectMapper;
    
    private static final Pattern PRODUCT_BY_ID_PATTERN = Pattern.compile("^/api/products/([^/]+)$");

    public ProductCQRSHandler(CQRSMediator mediator) {
        this.mediator = mediator;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules(); // For Java 8+ date/time support
    }

    public boolean canHandle(String uri) {
        return uri.startsWith("/api/products");
    }

    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        HttpMethod method = request.method();

        try {
            if (uri.equals("/api/products") && method.equals(HttpMethod.GET)) {
                handleGetAllProducts(ctx);
            } else if (uri.equals("/api/products") && method.equals(HttpMethod.POST)) {
                handleCreateProduct(ctx, request);
            } else if (PRODUCT_BY_ID_PATTERN.matcher(uri).matches()) {
                Matcher matcher = PRODUCT_BY_ID_PATTERN.matcher(uri);
                matcher.find();
                String id = matcher.group(1);
                
                if (method.equals(HttpMethod.GET)) {
                    handleGetProductById(ctx, id);
                } else if (method.equals(HttpMethod.PUT)) {
                    handleUpdateProduct(ctx, request, id);
                } else if (method.equals(HttpMethod.DELETE)) {
                    handleDeleteProduct(ctx, id);
                } else {
                    sendMethodNotAllowed(ctx);
                }
            } else {
                sendNotFound(ctx, "Endpoint not found");
            }
        } catch (IllegalArgumentException e) {
            sendBadRequest(ctx, e.getMessage());
        } catch (Exception e) {
            sendInternalError(ctx, e.getMessage());
        }
    }

    private void handleGetAllProducts(ChannelHandlerContext ctx) throws Exception {
        List<Product> products = mediator.getAllProducts(new GetAllProductsQuery());
        
        ArrayNode jsonArray = objectMapper.createArrayNode();
        for (Product product : products) {
            jsonArray.add(productToJson(product));
        }
        
        sendJsonResponse(ctx, HttpResponseStatus.OK, jsonArray.toString());
    }

    private void handleGetProductById(ChannelHandlerContext ctx, String id) throws Exception {
        Optional<Product> productOpt = mediator.getProductById(new GetProductByIdQuery(id));
        
        if (productOpt.isPresent()) {
            String json = objectMapper.writeValueAsString(productToJson(productOpt.get()));
            sendJsonResponse(ctx, HttpResponseStatus.OK, json);
        } else {
            sendNotFound(ctx, "Product not found with ID: " + id);
        }
    }

    private void handleCreateProduct(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String body = request.content().toString(StandardCharsets.UTF_8);
        JsonNode jsonNode = objectMapper.readTree(body);
        
        String name = jsonNode.get("name").asText();
        String description = jsonNode.has("description") ? jsonNode.get("description").asText() : null;
        BigDecimal price = new BigDecimal(jsonNode.get("price").asText());
        Integer quantity = jsonNode.get("quantity").asInt();
        
        CreateProductCommand command = new CreateProductCommand(name, description, price, quantity);
        Product product = mediator.createProduct(command);
        
        String json = objectMapper.writeValueAsString(productToJson(product));
        sendJsonResponse(ctx, HttpResponseStatus.CREATED, json);
    }

    private void handleUpdateProduct(ChannelHandlerContext ctx, FullHttpRequest request, String id) throws Exception {
        String body = request.content().toString(StandardCharsets.UTF_8);
        JsonNode jsonNode = objectMapper.readTree(body);
        
        String name = jsonNode.has("name") ? jsonNode.get("name").asText() : null;
        String description = jsonNode.has("description") ? jsonNode.get("description").asText() : null;
        BigDecimal price = jsonNode.has("price") ? new BigDecimal(jsonNode.get("price").asText()) : null;
        Integer quantity = jsonNode.has("quantity") ? jsonNode.get("quantity").asInt() : null;
        
        UpdateProductCommand command = new UpdateProductCommand(id, name, description, price, quantity);
        Product product = mediator.updateProduct(command);
        
        String json = objectMapper.writeValueAsString(productToJson(product));
        sendJsonResponse(ctx, HttpResponseStatus.OK, json);
    }

    private void handleDeleteProduct(ChannelHandlerContext ctx, String id) throws Exception {
        DeleteProductCommand command = new DeleteProductCommand(id);
        mediator.deleteProduct(command);
        
        ObjectNode response = objectMapper.createObjectNode();
        response.put("message", "Product deleted successfully");
        response.put("id", id);
        
        sendJsonResponse(ctx, HttpResponseStatus.OK, response.toString());
    }

    private ObjectNode productToJson(Product product) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("id", product.getId());
        node.put("name", product.getName());
        node.put("description", product.getDescription());
        node.put("price", product.getPrice().toString());
        node.put("quantity", product.getQuantity());
        node.put("createdAt", product.getCreatedAt().toString());
        node.put("updatedAt", product.getUpdatedAt().toString());
        return node;
    }

    private void sendJsonResponse(ChannelHandlerContext ctx, HttpResponseStatus status, String json) {
        byte[] content = json.getBytes(StandardCharsets.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                status,
                Unpooled.wrappedBuffer(content)
        );

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.length);
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendBadRequest(ChannelHandlerContext ctx, String message) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("error", message);
        sendJsonResponse(ctx, HttpResponseStatus.BAD_REQUEST, error.toString());
    }

    private void sendNotFound(ChannelHandlerContext ctx, String message) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("error", message);
        sendJsonResponse(ctx, HttpResponseStatus.NOT_FOUND, error.toString());
    }

    private void sendMethodNotAllowed(ChannelHandlerContext ctx) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("error", "Method not allowed");
        sendJsonResponse(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED, error.toString());
    }

    private void sendInternalError(ChannelHandlerContext ctx, String message) {
        ObjectNode error = objectMapper.createObjectNode();
        error.put("error", "Internal server error: " + message);
        sendJsonResponse(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, error.toString());
    }
}
