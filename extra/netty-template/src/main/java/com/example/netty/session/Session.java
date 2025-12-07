package com.example.netty.session;

import java.util.Map;

public class Session {
    
    private final String sessionId;
    private final Map<String, Object> userInfo;
    private final long createdAt;
    private final long expiresAt;
    
    private static final long SESSION_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    
    public Session(String sessionId, Map<String, Object> userInfo) {
        this.sessionId = sessionId;
        this.userInfo = userInfo;
        this.createdAt = System.currentTimeMillis();
        this.expiresAt = createdAt + SESSION_DURATION;
    }
    
    /**
     * Constructor for loading session from database
     */
    public Session(String sessionId, Map<String, Object> userInfo, long createdAt, long expiresAt) {
        this.sessionId = sessionId;
        this.userInfo = userInfo;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public Map<String, Object> getUserInfo() {
        return userInfo;
    }
    
    public long getCreatedAt() {
        return createdAt;
    }
    
    public long getExpiresAt() {
        return expiresAt;
    }
    
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }
}
