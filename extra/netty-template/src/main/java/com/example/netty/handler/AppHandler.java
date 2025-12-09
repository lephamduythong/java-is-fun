package com.example.netty.handler;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.UNAUTHORIZED;

import java.util.HashMap;
import java.util.Map;

import com.example.netty.common.WonderUtils;
import com.example.netty.session.Session;
import com.example.netty.session.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

public class AppHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SessionManager sessionManager = SessionManager.getInstance();

    public FullHttpResponse handleGetRequest(FullHttpRequest request) {
        // Check authentication
        String sessionId = WonderUtils.getSessionFromHeader(request);
        
        if (sessionId == null) {
            return WonderUtils.createJsonResponse(UNAUTHORIZED,
                    WonderUtils.createErrorJson("Unauthorized", "Session ID required in Authorization header. Please login first at /login"));
        }
        
        Session session = sessionManager.getSession(sessionId);
        if (session == null) {
            return WonderUtils.createJsonResponse(UNAUTHORIZED,
                    WonderUtils.createErrorJson("Unauthorized", "Invalid or expired session. Please login again at /login"));
        }
        
        // User is authenticated
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "Hello from Netty Server!");
        responseData.put("method", "GET");
        responseData.put("timestamp", System.currentTimeMillis());
        responseData.put("status", "success");
        responseData.put("user", session.getUserInfo().get("email"));
        
        return WonderUtils.createJsonResponse(OK, responseData);
    }

    public FullHttpResponse handlePostRequest(FullHttpRequest request) {
        // Check authentication
        String sessionId = WonderUtils.getSessionFromHeader(request);
        
        if (sessionId == null) {
            return WonderUtils.createJsonResponse(UNAUTHORIZED,
                    WonderUtils.createErrorJson("Unauthorized", "Session ID required in Authorization header. Please login first at /login"));
        }
        
        Session session = sessionManager.getSession(sessionId);
        if (session == null) {
            return WonderUtils.createJsonResponse(UNAUTHORIZED,
                    WonderUtils.createErrorJson("Unauthorized", "Invalid or expired session. Please login again at /login"));
        }
        
        // User is authenticated
        String contentBody = request.content().toString(CharsetUtil.UTF_8);
        
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "Data received successfully");
        responseData.put("method", "POST");
        responseData.put("receivedData", contentBody);
        responseData.put("timestamp", System.currentTimeMillis());
        responseData.put("status", "success");
        responseData.put("user", session.getUserInfo().get("email"));
        
        // Parse received JSON if possible
        try {
            if (contentBody != null && !contentBody.isEmpty()) {
                JsonNode receivedJson = objectMapper.readTree(contentBody);
                responseData.put("parsedData", receivedJson);
            }
        } catch (Exception e) {
            responseData.put("parseError", "Could not parse JSON: " + e.getMessage());
        }
        
        return WonderUtils.createJsonResponse(OK, responseData);
    }
}
