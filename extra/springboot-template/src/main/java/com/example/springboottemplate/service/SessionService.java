package com.example.springboottemplate.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * PostgreSQL-backed session store. Sessions expire after 24 hours.
 */
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public String createSession(Map<String, Object> userInfo) {
        String sessionId = UUID.randomUUID().toString();
        SessionData session = new SessionData(userInfo);
        sessionRepository.deleteExpiredSessions(session.getCreatedAt());
        sessionRepository.save(sessionId, session);
        return sessionId;
    }

    public SessionData getSession(String sessionId) {
        if (sessionId == null) return null;

        SessionData session = sessionRepository.findById(sessionId);
        if (session == null) return null;
        if (session.isExpired()) {
            deleteSession(sessionId);
            return null;
        }
        return session;
    }

    public void deleteSession(String sessionId) {
        if (sessionId != null) {
            sessionRepository.deleteById(sessionId);
        }
    }

    public static class SessionData {
        private final Map<String, Object> userInfo;
        private final LocalDateTime createdAt;
        private final LocalDateTime expiresAt;

        public SessionData(Map<String, Object> userInfo) {
            this(userInfo, LocalDateTime.now(), null);
        }

        public SessionData(
                Map<String, Object> userInfo,
                LocalDateTime createdAt,
                LocalDateTime expiresAt) {
            this.userInfo = userInfo;
            this.createdAt = createdAt;
            this.expiresAt = expiresAt != null ? expiresAt : createdAt.plusHours(24);
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiresAt);
        }

        public Map<String, Object> getUserInfo() { return userInfo; }
        public LocalDateTime getCreatedAt()       { return createdAt; }
        public LocalDateTime getExpiresAt()       { return expiresAt; }
    }
}
