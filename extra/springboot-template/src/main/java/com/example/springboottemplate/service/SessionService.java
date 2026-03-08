package com.example.springboottemplate.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory session store (replaces Netty's SessionManager).
 * Sessions expire after 24 hours.
 */
@Service
public class SessionService {

    private final Map<String, SessionData> sessions = new ConcurrentHashMap<>();

    public String createSession(Map<String, Object> userInfo) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new SessionData(userInfo));
        return sessionId;
    }

    public SessionData getSession(String sessionId) {
        if (sessionId == null) return null;
        SessionData session = sessions.get(sessionId);
        if (session == null) return null;
        if (session.isExpired()) {
            sessions.remove(sessionId);
            return null;
        }
        return session;
    }

    public void deleteSession(String sessionId) {
        if (sessionId != null) sessions.remove(sessionId);
    }

    public static class SessionData {
        private final Map<String, Object> userInfo;
        private final LocalDateTime createdAt;
        private final LocalDateTime expiresAt;

        public SessionData(Map<String, Object> userInfo) {
            this.userInfo = userInfo;
            this.createdAt = LocalDateTime.now();
            this.expiresAt = createdAt.plusHours(24);
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiresAt);
        }

        public Map<String, Object> getUserInfo() { return userInfo; }
        public LocalDateTime getCreatedAt()       { return createdAt; }
        public LocalDateTime getExpiresAt()       { return expiresAt; }
    }
}
