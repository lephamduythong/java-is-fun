package com.example.netty;

import com.example.netty.handler.OAuthHandler;
import com.example.netty.session.Session;
import com.example.netty.session.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    
    private final Gson gson = new Gson();
    private final SessionManager sessionManager = SessionManager.getInstance();
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpMethod method = request.method();
        
        System.out.println("Received " + method + " request for: " + uri);
        
        FullHttpResponse response;
        
        // OAuth routes
        if (method == HttpMethod.GET && uri.equals("/login")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleLoginRequest();
        }
        else if (method == HttpMethod.GET && uri.startsWith("/oauth/callback")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleOAuthCallback(uri);
        }
        else if (method == HttpMethod.GET && uri.equals("/profile")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleProfileRequest(request);
        }
        else if (method == HttpMethod.GET && uri.equals("/logout")) {
            var oauthHandler = new OAuthHandler();
            response = oauthHandler.handleLogoutRequest(request);
        }
        // Protected routes - require authentication
        else if (method == HttpMethod.GET && uri.startsWith("/hello")) {
            response = handleGetRequest(request);
        }
        else if (method == HttpMethod.POST && uri.startsWith("/data")) {
            response = handlePostRequest(request);
        }
        // 404 Not Found
        else {
            response = createJsonResponse(NOT_FOUND, 
                    createErrorJson("Not Found", "The requested endpoint does not exist"));
        }
        
        // Send response and close connection
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    private FullHttpResponse handleGetRequest(FullHttpRequest request) {
        // Check authentication
        String sessionId = getSessionFromHeader(request);
        
        if (sessionId == null) {
            return createJsonResponse(UNAUTHORIZED,
                    createErrorJson("Unauthorized", "Session ID required in Authorization header. Please login first at /login"));
        }
        
        Session session = sessionManager.getSession(sessionId);
        if (session == null) {
            return createJsonResponse(UNAUTHORIZED,
                    createErrorJson("Unauthorized", "Invalid or expired session. Please login again at /login"));
        }
        
        // User is authenticated
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "Hello from Netty Server!");
        responseData.put("method", "GET");
        responseData.put("timestamp", System.currentTimeMillis());
        responseData.put("status", "success");
        responseData.put("user", session.getUserInfo().get("email"));
        
        return createJsonResponse(OK, responseData);
    }
    
    private FullHttpResponse handlePostRequest(FullHttpRequest request) {
        // Check authentication
        String sessionId = getSessionFromHeader(request);
        
        if (sessionId == null) {
            return createJsonResponse(UNAUTHORIZED,
                    createErrorJson("Unauthorized", "Session ID required in Authorization header. Please login first at /login"));
        }
        
        Session session = sessionManager.getSession(sessionId);
        if (session == null) {
            return createJsonResponse(UNAUTHORIZED,
                    createErrorJson("Unauthorized", "Invalid or expired session. Please login again at /login"));
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
                JsonObject receivedJson = gson.fromJson(contentBody, JsonObject.class);
                responseData.put("parsedData", receivedJson);
            }
        } catch (Exception e) {
            responseData.put("parseError", "Could not parse JSON: " + e.getMessage());
        }
        
        return createJsonResponse(OK, responseData);
    }
    
    private Map<String, String> createErrorJson(String error, String message) {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("error", error);
        errorData.put("message", message);
        return errorData;
    }
    
    /**
     * Extract session ID from Authorization header
     * Expected format: "Bearer {sessionId}"
     */
    private String getSessionFromHeader(FullHttpRequest request) {
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
    
    private FullHttpResponse createJsonResponse(HttpResponseStatus status, Object data) {
        String jsonResponse = gson.toJson(data);
        
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1,
                status,
                Unpooled.copiedBuffer(jsonResponse, CharsetUtil.UTF_8)
        );
        
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        
        return response;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
