package com.example.netty.oauth;

import com.example.netty.config.OAuthConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GoogleOAuthHandler {
    
    private static GoogleOAuthHandler instance;
    private final OAuthConfig config;
    private final ObjectMapper objectMapper;
    private final Map<String, String> stateStore; // Store state tokens to prevent CSRF
    
    private GoogleOAuthHandler() {
        this.config = OAuthConfig.getInstance();
        this.objectMapper = new ObjectMapper();
        this.stateStore = new ConcurrentHashMap<>();
    }
    
    public static GoogleOAuthHandler getInstance() {
        if (instance == null) {
            synchronized (GoogleOAuthHandler.class) {
                if (instance == null) {
                    instance = new GoogleOAuthHandler();
                }
            }
        }
        return instance;
    }
    
    /**
     * Generate Google OAuth authorization URL
     */
    public String getAuthorizationUrl() {
        // Generate random state for CSRF protection
        String state = UUID.randomUUID().toString();
        stateStore.put(state, state);
        
        String scope = "email profile openid";
        
        try {
            StringBuilder url = new StringBuilder();
            url.append(config.getAuthUri());
            url.append("?client_id=").append(URLEncoder.encode(config.getClientId(), "UTF-8"));
            url.append("&redirect_uri=").append(URLEncoder.encode(config.getRedirectUri(), "UTF-8"));
            url.append("&response_type=code");
            url.append("&scope=").append(URLEncoder.encode(scope, "UTF-8"));
            url.append("&state=").append(URLEncoder.encode(state, "UTF-8"));
            url.append("&access_type=offline");
            url.append("&prompt=consent");

            System.out.println("Generated Google OAuth Authorization URL:");
            System.out.println(url);
            
            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate authorization URL", e);
        }
    }
    
    /**
     * Verify state token
     */
    public boolean verifyState(String state) {
        return stateStore.containsKey(state);
    }
    
    /**
     * Exchange authorization code for access token
     */
    public Map<String, Object> exchangeCodeForToken(String code) throws Exception {
        URL url = new URL(config.getTokenUri());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        
        // Build request body
        StringBuilder postData = new StringBuilder();
        postData.append("code=").append(URLEncoder.encode(code, "UTF-8"));
        postData.append("&client_id=").append(URLEncoder.encode(config.getClientId(), "UTF-8"));
        postData.append("&client_secret=").append(URLEncoder.encode(config.getClientSecret(), "UTF-8"));
        postData.append("&redirect_uri=").append(URLEncoder.encode(config.getRedirectUri(), "UTF-8"));
        postData.append("&grant_type=authorization_code");
        
        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.toString().getBytes(StandardCharsets.UTF_8));
        }
        
        // Read response
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            
            JsonNode jsonResponse = objectMapper.readTree(response.toString());
            Map<String, Object> result = new HashMap<>();
            result.put("access_token", jsonResponse.get("access_token").asText());
            
            if (jsonResponse.has("refresh_token")) {
                result.put("refresh_token", jsonResponse.get("refresh_token").asText());
            }
            if (jsonResponse.has("expires_in")) {
                result.put("expires_in", jsonResponse.get("expires_in").asInt());
            }
            
            return result;
        } else {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = errorReader.readLine()) != null) {
                errorResponse.append(line);
            }
            errorReader.close();
            throw new Exception("Failed to exchange code for token: " + errorResponse.toString());
        }
    }
    
    /**
     * Get user info using access token
     */
    public Map<String, Object> getUserInfo(String accessToken) throws Exception {
        URL url = new URL(config.getUserInfoUri());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            
            JsonNode jsonResponse = objectMapper.readTree(response.toString());
            Map<String, Object> userInfo = new HashMap<>();
            
            if (jsonResponse.has("id")) {
                userInfo.put("id", jsonResponse.get("id").asText());
            }
            if (jsonResponse.has("email")) {
                userInfo.put("email", jsonResponse.get("email").asText());
            }
            if (jsonResponse.has("name")) {
                userInfo.put("name", jsonResponse.get("name").asText());
            }
            if (jsonResponse.has("picture")) {
                userInfo.put("picture", jsonResponse.get("picture").asText());
            }
            if (jsonResponse.has("verified_email")) {
                userInfo.put("verified_email", jsonResponse.get("verified_email").asBoolean());
            }
            
            return userInfo;
        } else {
            throw new Exception("Failed to get user info. Response code: " + responseCode);
        }
    }
}
