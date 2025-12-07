package com.example.netty.session;

import com.example.netty.config.AppConfig;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SessionManager {
    
    private static SessionManager instance;
    private final Map<String, Session> sessions; // In-memory cache
    private final SessionDatabase database;
    private final boolean useSqlite;
    private final ScheduledExecutorService cleanupScheduler;
    private final int cleanupIntervalHours;
    
    private SessionManager() {
        this.sessions = new ConcurrentHashMap<>();
        
        // Load configuration from config.properties
        AppConfig config = AppConfig.getInstance();
        this.useSqlite = config.isUseSqliteSession();
        this.cleanupIntervalHours = config.getSessionCleanupIntervalHours();
        
        if (useSqlite) {
            this.database = SessionDatabase.getInstance();
            System.out.println("SessionManager: Using SQLite for persistent session storage");
        } else {
            this.database = null;
            System.out.println("SessionManager: Using in-memory session storage");
        }
        
        // Start cleanup scheduler to remove expired sessions
        this.cleanupScheduler = Executors.newSingleThreadScheduledExecutor();
        startCleanupTask();
    }
    
    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Start periodic cleanup task
     */
    private void startCleanupTask() {
        cleanupScheduler.scheduleAtFixedRate(() -> {
            try {
                clearExpiredSessions();
                System.out.println("Expired sessions cleaned up. Active sessions: " + getActiveSessionCount());
            } catch (Exception e) {
                System.err.println("Error during session cleanup: " + e.getMessage());
            }
        }, cleanupIntervalHours, cleanupIntervalHours, TimeUnit.HOURS);
    }
    
    /**
     * Create a new session
     */
    public String createSession(Map<String, Object> userInfo) {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId, userInfo);
        
        // Store in memory
        sessions.put(sessionId, session);
        
        // Store in database if enabled
        if (useSqlite && database != null) {
            database.saveSession(session);
        }
        
        return sessionId;
    }
    
    /**
     * Get session by ID
     */
    public Session getSession(String sessionId) {
        // Try to get from memory cache first
        Session session = sessions.get(sessionId);
        
        if (session != null) {
            if (!session.isExpired()) {
                return session;
            } else {
                // Remove expired session
                sessions.remove(sessionId);
                if (useSqlite && database != null) {
                    database.deleteSession(sessionId);
                }
                return null;
            }
        }
        
        // If using SQLite and not in cache, try to load from database
        if (useSqlite && database != null) {
            session = database.getSession(sessionId);
            if (session != null && !session.isExpired()) {
                // Cache it in memory
                sessions.put(sessionId, session);
                return session;
            }
        }
        
        return null;
    }
    
    /**
     * Delete session
     */
    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
        
        if (useSqlite && database != null) {
            database.deleteSession(sessionId);
        }
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
        // Clear from memory
        sessions.entrySet().removeIf(entry -> entry.getValue().isExpired());
        
        // Clear from database if enabled
        if (useSqlite && database != null) {
            int removed = database.clearExpiredSessions();
            System.out.println("Removed " + removed + " expired sessions from database");
        }
    }
    
    /**
     * Get count of active sessions
     */
    public int getActiveSessionCount() {
        if (useSqlite && database != null) {
            return database.getActiveSessionCount();
        }
        return (int) sessions.values().stream().filter(s -> !s.isExpired()).count();
    }
    
    /**
     * Check if using SQLite storage
     */
    public boolean isUsingSqlite() {
        return useSqlite;
    }
    
    /**
     * Shutdown manager and cleanup resources
     */
    public void shutdown() {
        cleanupScheduler.shutdown();
        try {
            if (!cleanupScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                cleanupScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            cleanupScheduler.shutdownNow();
        }
        
        if (useSqlite && database != null) {
            database.close();
        }
        
        System.out.println("SessionManager shutdown complete");
    }
}
