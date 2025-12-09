package com.example.netty.common;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

public class WonderUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static FullHttpResponse createJsonResponse(HttpResponseStatus status, Object data) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(data);
            
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_1_1,
                    status,
                    Unpooled.copiedBuffer(jsonResponse, CharsetUtil.UTF_8)
            );
            
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize JSON", e);
        }
    }
    
    public static Map<String, String> createErrorJson(String error, String message) {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("error", error);
        errorData.put("message", message);
        return errorData;
    }
    
    public static FullHttpResponse createHtmlResponse(String title, String body) {
        String html = "<!DOCTYPE html>" +
                "<html><head><title>" + title + "</title></head>" +
                "<body>" + body + "</body></html>";
        
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(html, CharsetUtil.UTF_8)
        );
        
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        
        return response;
    }
    
    public static Map<String, String> parseQueryParams(String uri) {
        Map<String, String> params = new HashMap<>();
        
        int queryStart = uri.indexOf('?');
        if (queryStart == -1) {
            return params;
        }
        
        String query = uri.substring(queryStart + 1);
        String[] pairs = query.split("&");
        
        for (String pair : pairs) {
            int idx = pair.indexOf('=');
            if (idx > 0) {
                try {
                    String key = java.net.URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                    String value = java.net.URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                    params.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return params;
    }
    
    /**
     * Extract session ID from Authorization header
     * Expected format: "Bearer {sessionId}"
     */
    public static String getSessionFromHeader(FullHttpRequest request) {
        String authHeader = request.headers().get(HttpHeaderNames.AUTHORIZATION);
        
        if (authHeader == null || authHeader.isEmpty()) {
            return null;
        }
        
        // Support both "Bearer {token}" and just "{token}"
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7).trim();
        }
        
        return authHeader.trim();
    }
}
