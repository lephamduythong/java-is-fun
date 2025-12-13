package com.example.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class StaticFileHandler {
    
    private static final String STATIC_DIR = "/static";
    
    public FullHttpResponse handleStaticFileRequest(String uri) {
        try {
            // Decode URI
            String decodedUri = URLDecoder.decode(uri, StandardCharsets.UTF_8.name());
            
            // Remove query parameters if present
            int queryIndex = decodedUri.indexOf('?');
            if (queryIndex != -1) {
                decodedUri = decodedUri.substring(0, queryIndex);
            }
            
            // Security check: prevent directory traversal
            if (decodedUri.contains("..") || decodedUri.contains("./") || decodedUri.contains("/.")) {
                return createErrorResponse(FORBIDDEN, "Access denied");
            }
            
            // Build resource path
            String resourcePath = STATIC_DIR + decodedUri;
            
            // Try to load resource from classpath
            InputStream inputStream = getClass().getResourceAsStream(resourcePath);
            
            if (inputStream == null) {
                return createErrorResponse(NOT_FOUND, "File not found: " + decodedUri);
            }
            
            // Read file content
            byte[] fileContent = inputStream.readAllBytes();
            inputStream.close();
            
            // Determine content type
            String contentType = getContentType(decodedUri);
            
            // Create response
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_1_1, 
                    OK, 
                    Unpooled.copiedBuffer(fileContent)
            );
            
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileContent.length);
            response.headers().set(HttpHeaderNames.CACHE_CONTROL, "max-age=3600"); // Cache for 1 hour
            
            return response;
            
        } catch (Exception e) {
            e.printStackTrace();
            return createErrorResponse(INTERNAL_SERVER_ERROR, "Error serving file: " + e.getMessage());
        }
    }
    
    private String getContentType(String uri) {
        String lowerUri = uri.toLowerCase();
        
        if (lowerUri.endsWith(".html") || lowerUri.endsWith(".htm")) {
            return "text/html; charset=UTF-8";
        } else if (lowerUri.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        } else if (lowerUri.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        } else if (lowerUri.endsWith(".json")) {
            return "application/json; charset=UTF-8";
        } else if (lowerUri.endsWith(".png")) {
            return "image/png";
        } else if (lowerUri.endsWith(".jpg") || lowerUri.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerUri.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerUri.endsWith(".svg")) {
            return "image/svg+xml";
        } else if (lowerUri.endsWith(".ico")) {
            return "image/x-icon";
        } else if (lowerUri.endsWith(".pdf")) {
            return "application/pdf";
        } else if (lowerUri.endsWith(".txt")) {
            return "text/plain; charset=UTF-8";
        } else if (lowerUri.endsWith(".xml")) {
            return "application/xml; charset=UTF-8";
        } else if (lowerUri.endsWith(".zip")) {
            return "application/zip";
        } else {
            return "application/octet-stream";
        }
    }
    
    private FullHttpResponse createErrorResponse(HttpResponseStatus status, String message) {
        String errorHtml = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head><title>" + status.code() + " " + status.reasonPhrase() + "</title></head>\n" +
                "<body>\n" +
                "<h1>" + status.code() + " " + status.reasonPhrase() + "</h1>\n" +
                "<p>" + message + "</p>\n" +
                "</body>\n" +
                "</html>";
        
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1,
                status,
                Unpooled.copiedBuffer(errorHtml, CharsetUtil.UTF_8)
        );
        
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        
        return response;
    }
}
