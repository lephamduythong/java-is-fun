package com.example.netty.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    
    private static SessionManager instance;
    private final Map<String, Session> sessions;
    
    private SessionManager() {
        this.sessions = new ConcurrentHashMap<>();
    }
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    /**
     * Create a new session
     */
    public String createSession(Map<String, Object> userInfo) {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId, userInfo);
        sessions.put(sessionId, session);
        return sessionId;
    }
    
    /**
     * Get session by ID
     */
    public Session getSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null && !session.isExpired()) {
            return session;
        } else if (session != null && session.isExpired()) {
            sessions.remove(sessionId);
        }
        return null;
    }
    
    /**
     * Delete session
     */
    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
    }
    
    /**
     * Check if session exists and is valid
     */
    public boolean isValidSession(String sessionId) {
        Session session = getSession(sessionId);
        return session != null;
    }
    
    /**
     * Clear all expired sessions
     */
    public void clearExpiredSessions() {
        sessions.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
