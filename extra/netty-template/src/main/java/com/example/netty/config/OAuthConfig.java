package com.example.netty.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OAuthConfig {
    
    private static final String CONFIG_FILE = "src/main/resources/configs/oauth.properties";
    
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String authUri = "https://accounts.google.com/o/oauth2/v2/auth";
    private String tokenUri = "https://oauth2.googleapis.com/token";
    private String userInfoUri = "https://www.googleapis.com/oauth2/v2/userinfo";
    
    private static OAuthConfig instance;
    
    private OAuthConfig() {
        loadConfig();
    }
    
    public static OAuthConfig getInstance() {
        if (instance == null) {
            instance = new OAuthConfig();
        }
        return instance;
    }
    
    private void loadConfig() {
        Properties props = new Properties();
        
        // Try to load from file first
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            this.clientId = props.getProperty("google.client.id");
            this.clientSecret = props.getProperty("google.client.secret");
            this.redirectUri = props.getProperty("google.redirect.uri", "http://localhost:8080/oauth/callback");
        } catch (IOException e) {
            // If file doesn't exist, try to load from environment variables
            this.clientId = System.getenv("GOOGLE_CLIENT_ID");
            this.clientSecret = System.getenv("GOOGLE_CLIENT_SECRET");
            this.redirectUri = System.getenv("GOOGLE_REDIRECT_URI");
            
            if (this.redirectUri == null) {
                this.redirectUri = "http://localhost:8080/oauth/callback";
            }
        }
        
        if (clientId == null || clientSecret == null) {
            System.err.println("WARNING: Google OAuth credentials not configured!");
            System.err.println("Please create 'oauth.properties' file or set environment variables:");
            System.err.println("  - GOOGLE_CLIENT_ID");
            System.err.println("  - GOOGLE_CLIENT_SECRET");
            System.err.println("  - GOOGLE_REDIRECT_URI (optional)");
        }
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public String getClientSecret() {
        return clientSecret;
    }
    
    public String getRedirectUri() {
        return redirectUri;
    }
    
    public String getAuthUri() {
        return authUri;
    }
    
    public String getTokenUri() {
        return tokenUri;
    }
    
    public String getUserInfoUri() {
        return userInfoUri;
    }
    
    public boolean isConfigured() {
        return clientId != null && clientSecret != null;
    }
}
