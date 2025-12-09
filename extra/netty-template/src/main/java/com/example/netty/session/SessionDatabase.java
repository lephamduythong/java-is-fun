package com.example.netty.session;

import com.example.netty.config.AppConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.Map;

public class SessionDatabase {
    
    private static SessionDatabase instance;
    private Connection connection;
    private final ObjectMapper objectMapper;
    private final String dbUrl;
    
    private SessionDatabase() {
        this.objectMapper = new ObjectMapper();
        AppConfig config = AppConfig.getInstance();
        String dbPath = config.getSqliteDatabasePath();
        this.dbUrl = "jdbc:sqlite:" + dbPath;
        initializeDatabase();
    }
    
    public static SessionDatabase getInstance() {
        if (instance == null) {
            synchronized (SessionDatabase.class) {
                if (instance == null) {
                    instance = new SessionDatabase();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize database and create sessions table
     */
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(dbUrl);
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS sessions (" +
                    "session_id TEXT PRIMARY KEY, " +
                    "user_info TEXT NOT NULL, " +
                    "created_at INTEGER NOT NULL, " +
                    "expires_at INTEGER NOT NULL)";
            
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }
            
            // Create index for faster expiration queries
            String createIndexSQL = "CREATE INDEX IF NOT EXISTS idx_expires_at ON sessions(expires_at)";
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createIndexSQL);
            }
            
            System.out.println("SQLite database initialized successfully at: " + dbUrl);
            
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Save session to database
     */
    public boolean saveSession(Session session) {
        String sql = "INSERT OR REPLACE INTO sessions (session_id, user_info, created_at, expires_at) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, session.getSessionId());
            pstmt.setString(2, objectMapper.writeValueAsString(session.getUserInfo()));
            pstmt.setLong(3, session.getCreatedAt());
            pstmt.setLong(4, session.getExpiresAt());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (Exception e) {
            System.err.println("Failed to save session: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get session from database
     */
    public Session getSession(String sessionId) {
        String sql = "SELECT * FROM sessions WHERE session_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sessionId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userInfoJson = rs.getString("user_info");
                    long createdAt = rs.getLong("created_at");
                    long expiresAt = rs.getLong("expires_at");
                    
                    // Parse user info JSON back to Map
                    TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
                    Map<String, Object> userInfo = objectMapper.readValue(userInfoJson, typeRef);
                    
                    // Create session object
                    Session session = new Session(sessionId, userInfo, createdAt, expiresAt);
                    
                    // Check if expired
                    if (session.isExpired()) {
                        deleteSession(sessionId);
                        return null;
                    }
                    
                    return session;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Failed to get session: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Delete session from database
     */
    public boolean deleteSession(String sessionId) {
        String sql = "DELETE FROM sessions WHERE session_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sessionId);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Failed to delete session: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete all expired sessions
     */
    public int clearExpiredSessions() {
        String sql = "DELETE FROM sessions WHERE expires_at < ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, System.currentTimeMillis());
            return pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Failed to clear expired sessions: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * Get total number of active sessions
     */
    public int getActiveSessionCount() {
        String sql = "SELECT COUNT(*) FROM sessions WHERE expires_at > ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, System.currentTimeMillis());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Failed to get session count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Close database connection
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
