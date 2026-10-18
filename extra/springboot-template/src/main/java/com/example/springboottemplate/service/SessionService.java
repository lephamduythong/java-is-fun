package com.example.springboottemplate.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PostgreSQL-backed session store with an in-memory L1 cache.
 * Session expiry is configured in minutes with the session.timeout property.
 */
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final long sessionTimeoutMinutes;
    private final Map<String, SessionData> sessionCache = new ConcurrentHashMap<>();

    public SessionService(
            SessionRepository sessionRepository,
            @Value("${session.timeout:180}") long sessionTimeoutMinutes) {
        if (sessionTimeoutMinutes <= 0) {
            throw new IllegalArgumentException("session.timeout must be greater than zero");
        }
        this.sessionRepository = sessionRepository;
        this.sessionTimeoutMinutes = sessionTimeoutMinutes;
    }

    public String createSession(Map<String, Object> userInfo) {
        String sessionId = UUID.randomUUID().toString();
        SessionData session = new SessionData(userInfo, LocalDateTime.now(), sessionTimeoutMinutes);
        sessionRepository.deleteExpiredSessions(
                session.getCreatedAt().minusMinutes(sessionTimeoutMinutes));
        sessionRepository.save(sessionId, session);
        removeExpiredCachedSessions();
        sessionCache.put(sessionId, session);
        return sessionId;
    }

    public SessionData getSession(String sessionId) {
        if (sessionId == null) return null;

        return sessionCache.compute(sessionId, (id, cachedSession) -> {
            SessionData session = cachedSession != null
                    ? cachedSession
                    : sessionRepository.findById(id, sessionTimeoutMinutes);
            if (session == null) return null;
            if (session.isExpired()) {
                sessionRepository.deleteById(id);
                return null;
            }
            return session;
        });
    }

    public void deleteSession(String sessionId) {
        if (sessionId != null) {
            sessionCache.compute(sessionId, (id, cachedSession) -> {
                sessionRepository.deleteById(id);
                return null;
            });
        }
    }

    private void removeExpiredCachedSessions() {
        sessionCache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    public static class SessionData {
        private final Map<String, Object> userInfo;
        private final LocalDateTime createdAt;
        private final LocalDateTime expiresAt;

        public SessionData(
                Map<String, Object> userInfo,
                LocalDateTime createdAt,
                long sessionTimeoutMinutes) {
            this.userInfo = userInfo;
            this.createdAt = createdAt;
            this.expiresAt = createdAt.plusMinutes(sessionTimeoutMinutes);
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiresAt);
        }

        public Map<String, Object> getUserInfo() { return userInfo; }
        public LocalDateTime getCreatedAt()       { return createdAt; }
        public LocalDateTime getExpiresAt()       { return expiresAt; }
    }
}
