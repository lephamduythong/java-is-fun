package com.example.springboottemplate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class SessionRepository {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS test.oauth_sessions ("
                    + "session_id VARCHAR(36) PRIMARY KEY, "
                    + "user_info JSONB NOT NULL, "
                    + "created_at TIMESTAMP NOT NULL, "
                    + "expires_at TIMESTAMP NOT NULL)";
    private static final String CREATE_EXPIRY_INDEX_SQL =
            "CREATE INDEX IF NOT EXISTS idx_oauth_sessions_expires_at "
                    + "ON test.oauth_sessions (expires_at)";
    private static final TypeReference<Map<String, Object>> USER_INFO_TYPE =
            new TypeReference<Map<String, Object>>() { };

    private final DataSource dataSource;
    private final ObjectMapper objectMapper;

    public SessionRepository(
            @Value("${postgres.jndi-name}") String jndiName,
            ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        try {
            this.dataSource = (DataSource) new InitialContext().lookup(jndiName);
        } catch (NamingException e) {
            throw new IllegalStateException(
                    "Failed to look up PostgreSQL datasource: " + jndiName, e);
        }
        initializeSchema();
    }

    public void save(String sessionId, SessionService.SessionData session) {
        String sql = "INSERT INTO test.oauth_sessions "
                + "(session_id, user_info, created_at, expires_at) "
                + "VALUES (?, ?::jsonb, ?, ?)";
        try {
            String userInfoJson = objectMapper.writeValueAsString(session.getUserInfo());
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, sessionId);
                statement.setString(2, userInfoJson);
                statement.setTimestamp(3, Timestamp.valueOf(session.getCreatedAt()));
                statement.setTimestamp(4, Timestamp.valueOf(session.getExpiresAt()));
                statement.executeUpdate();
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize session user information", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save session in PostgreSQL", e);
        }
    }

    public SessionService.SessionData findById(String sessionId) {
        String sql = "SELECT user_info::text, created_at, expires_at "
                + "FROM test.oauth_sessions WHERE session_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sessionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) return null;

                Map<String, Object> userInfo =
                        objectMapper.readValue(resultSet.getString("user_info"), USER_INFO_TYPE);
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime expiresAt = resultSet.getTimestamp("expires_at").toLocalDateTime();
                return new SessionService.SessionData(userInfo, createdAt, expiresAt);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize session user information", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to read session from PostgreSQL", e);
        }
    }

    public void deleteById(String sessionId) {
        String sql = "DELETE FROM test.oauth_sessions WHERE session_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sessionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete session from PostgreSQL", e);
        }
    }

    public void deleteExpiredSessions(LocalDateTime now) {
        String sql = "DELETE FROM test.oauth_sessions WHERE expires_at <= ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(now));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete expired sessions from PostgreSQL", e);
        }
    }

    private void initializeSchema() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement createTable = connection.prepareStatement(CREATE_TABLE_SQL);
             PreparedStatement createIndex = connection.prepareStatement(CREATE_EXPIRY_INDEX_SQL)) {
            createTable.execute();
            createIndex.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize PostgreSQL session table", e);
        }
    }
}
