package com.example.netty;

import com.example.netty.oauth.GoogleOAuthHandler;
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
    private final GoogleOAuthHandler oauthHandler = GoogleOAuthHandler.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpMethod method = request.method();
        
        System.out.println("Received " + method + " request for: " + uri);
        
        FullHttpResponse response;
        
        // OAuth routes
        if (method == HttpMethod.GET && uri.equals("/login")) {
            response = handleLoginRequest();
        }
        else if (method == HttpMethod.GET && uri.startsWith("/oauth/callback")) {
            response = handleOAuthCallback(uri);
        }
        else if (method == HttpMethod.GET && uri.equals("/profile")) {
            response = handleProfileRequest(request);
        }
        else if (method == HttpMethod.GET && uri.equals("/logout")) {
            response = handleLogoutRequest(request);
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
    
    private FullHttpResponse handleLoginRequest() {
        try {
            String authUrl = oauthHandler.getAuthorizationUrl();
            
            // Create HTML redirect response
            String html = "<!DOCTYPE html>" +
                    "<html><head><title>Login with Google</title></head>" +
                    "<body>" +
                    "<h2>Redirecting to Google Login...</h2>" +
                    "<p>If you are not redirected automatically, <a href=\"" + authUrl + "\">click here</a>.</p>" +
                    "<script>window.location.href = \"" + authUrl + "\";</script>" +
                    "</body></html>";
            
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_1_1,
                    OK,
                    Unpooled.copiedBuffer(html, CharsetUtil.UTF_8)
            );
            
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            
            return response;
        } catch (Exception e) {
            return createJsonResponse(INTERNAL_SERVER_ERROR,
                    createErrorJson("Error", "Failed to generate login URL: " + e.getMessage()));
        }
    }
    
    private FullHttpResponse handleOAuthCallback(String uri) {
        try {
            // Parse query parameters
            Map<String, String> params = parseQueryParams(uri);
            String code = params.get("code");
            String state = params.get("state");
            String error = params.get("error");
            
            if (error != null) {
                return createHtmlResponse("Login Failed", 
                        "<h2>Login Failed</h2><p>Error: " + error + "</p>");
            }
            
            if (code == null) {
                return createHtmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>No authorization code received</p>");
            }
            
            // Verify state token
            if (!oauthHandler.verifyState(state)) {
                return createHtmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>Invalid state token (CSRF protection)</p>");
            }
            
            // Exchange code for token
            Map<String, Object> tokenData = oauthHandler.exchangeCodeForToken(code);
            String accessToken = (String) tokenData.get("access_token");
            
            // Get user info
            Map<String, Object> userInfo = oauthHandler.getUserInfo(accessToken);
            
            // Create session
            String sessionId = sessionManager.createSession(userInfo);
            
            // Create success HTML with user info
            String html = "<!DOCTYPE html>" +
                    "<html><head><title>Login Successful</title></head>" +
                    "<body>" +
                    "<h2>Login Successful!</h2>" +
                    "<p>Welcome, " + userInfo.get("name") + "!</p>" +
                    "<p>Email: " + userInfo.get("email") + "</p>" +
                    "<p>Session ID: " + sessionId + "</p>" +
                    "<p>Your session will expire in 24 hours.</p>" +
                    "<br/>" +
                    "<p><a href='/profile?session=" + sessionId + "'>View Profile</a> | " +
                    "<a href='/logout?session=" + sessionId + "'>Logout</a></p>" +
                    "</body></html>";
            
            return createHtmlResponse("Login Successful", html);
            
        } catch (Exception e) {
            e.printStackTrace();
            return createHtmlResponse("Login Failed",
                    "<h2>Login Failed</h2><p>Error: " + e.getMessage() + "</p>");
        }
    }
    
    private FullHttpResponse handleProfileRequest(FullHttpRequest request) {
        try {
            String sessionId = getSessionFromHeader(request);
            
            if (sessionId == null) {
                return createJsonResponse(UNAUTHORIZED,
                        createErrorJson("Unauthorized", "Session ID required in Authorization header"));
            }
            
            Session session = sessionManager.getSession(sessionId);
            if (session == null) {
                return createJsonResponse(UNAUTHORIZED,
                        createErrorJson("Unauthorized", "Invalid or expired session"));
            }
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("user", session.getUserInfo());
            responseData.put("sessionCreated", session.getCreatedAt());
            responseData.put("sessionExpires", session.getExpiresAt());
            
            return createJsonResponse(OK, responseData);
            
        } catch (Exception e) {
            return createJsonResponse(INTERNAL_SERVER_ERROR,
                    createErrorJson("Error", e.getMessage()));
        }
    }
    
    private FullHttpResponse handleLogoutRequest(FullHttpRequest request) {
        try {
            String sessionId = getSessionFromHeader(request);
            
            if (sessionId != null) {
                sessionManager.deleteSession(sessionId);
            }
            
            return createHtmlResponse("Logged Out",
                    "<h2>Logged Out Successfully</h2><p><a href='/login'>Login Again</a></p>");
            
        } catch (Exception e) {
            return createJsonResponse(INTERNAL_SERVER_ERROR,
                    createErrorJson("Error", e.getMessage()));
        }
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
    
    private FullHttpResponse createHtmlResponse(String title, String body) {
        String html = "<!DOCTYPE html>" +
                "<html><head><title>" + title + "</title></head>" +
                "<body>" + body + "</body></html>";
        
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1,
                OK,
                Unpooled.copiedBuffer(html, CharsetUtil.UTF_8)
        );
        
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        
        return response;
    }
    
    private Map<String, String> parseQueryParams(String uri) {
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
