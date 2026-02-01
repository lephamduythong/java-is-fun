package com.vibi.api.store;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class VibiStoreContextAPI { // WARNING: Change this name class for only 1 DB name only

    private static volatile VibiStoreContextAPI instance;
    private HikariDataSource dataSource;
    private final String dbPath;
    private static final String BASE_DIR = "./VIBI_STORE_CONTEXT_API/"; // WARNING: Change this BASE_DIR

    // Private constructor to ensure singleton pattern
    private VibiStoreContextAPI() throws SQLException {
        // Use class name as database name
        String databaseName = this.getClass().getSimpleName() + ".db";
        // Create directory if it doesn't exist
        File directory = new File(BASE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Full path to database file
        this.dbPath = BASE_DIR + databaseName;
        File dbFile = new File(this.dbPath);
        boolean isNewDatabase = !dbFile.exists();

        // Initialize HikariCP connection pool
        initializeConnectionPool();

        // If new database, enable WAL mode
        if (isNewDatabase) {
            enableWALMode();
        }

        // Create default table
        createDefaultTable();
    }

    private VibiStoreContextAPI(String customPath) throws SQLException {
        // Use custom path for database file
        this.dbPath = customPath;
        File dbFile = new File(this.dbPath);
        boolean isNewDatabase = !dbFile.exists();

        // Initialize HikariCP connection pool
        initializeConnectionPool();

        // If new database, enable WAL mode
        if (isNewDatabase) {
            enableWALMode();
        }

        // Create default table
        createDefaultTable();
    }

    /**
     * Get instance of VibiStoreContextAPI (thread-safe double-checked locking)
     * Database name will automatically be set to the class name (e.g., "VibiStoreContextAPI.db")
     * 
     * @return Unique instance of the class
     * @throws SQLException If error occurs during database initialization
     */
    public static VibiStoreContextAPI getInstance() throws SQLException {
        if (instance == null) {
            synchronized (VibiStoreContextAPI.class) {
                if (instance == null) {
                    instance = new VibiStoreContextAPI();
                }
            }
        }
        return instance;
    }

    /**
     * Get instance of VibiStoreContextAPI with custom database path (thread-safe double-checked locking)
     * 
     * @param customPath Custom path for the database file
     * @return Unique instance of the class
     * @throws SQLException If error occurs during database initialization
     */
    public static VibiStoreContextAPI getInstance(String customPath) throws SQLException {
        if (instance == null) {
            synchronized (VibiStoreContextAPI.class) {
                if (instance == null) {
                    instance = new VibiStoreContextAPI(customPath);
                }
            }
        }
        return instance;
    }

    /**
     * Initialize HikariCP connection pool
     */
    private void initializeConnectionPool() throws SQLException {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Configure HikariCP
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlite:" + dbPath);
            config.setMaximumPoolSize(1); // Maximum 1 connection in pool
            config.setMinimumIdle(1); // Minimum 1 idle connection
            config.setConnectionTimeout(30000); // 30 seconds
            config.setIdleTimeout(600000); // 10 minutes
            config.setMaxLifetime(1800000); // 30 minutes
            config.setPoolName("VibiStoreContextPool");
            config.setConnectionTestQuery("SELECT 1"); // Test query for connection validation
            config.setKeepaliveTime(300000); // 5 minutes keepalive
            
            // SQLite specific settings
            config.addDataSourceProperty("journal_mode", "WAL");
            
            // Create data source
            dataSource = new HikariDataSource(config);

        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }

    /**
     * Enable WAL (Write-Ahead Logging) mode for database
     */
    private void enableWALMode() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL;");
        }
    }

    /**
     * Create default table with ID and VALUE columns
     */
    private void createDefaultTable() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS store_context (" +
                    "ID TEXT PRIMARY KEY, " +
                    "VALUE TEXT)");
        }
    }

    /**
     * Get current connection from pool (thread-safe)
     * 
     * @return Connection object from pool
     * @throws SQLException If connection pool is closed or invalid
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new SQLException("Connection pool is closed");
        }
        return dataSource.getConnection();
    }

    /**
     * Close connection pool and all connections
     */
    public void closeConnection() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Get full path to database file
     * 
     * @return Database path
     */
    public String getDatabasePath() {
        return dbPath;
    }

    /**
     * Write data to store_context table
     * 
     * @param id    The ID value (primary key)
     * @param value The VALUE to store
     * @throws SQLException If write operation fails
     */
    public void write(String id, String value) throws SQLException {
        String sql = "INSERT OR REPLACE INTO store_context (ID, VALUE) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            retryResetConnectionPool();
        }
    }

    /**
     * Read data from store_context table by ID
     * 
     * @param id The ID to look up
     * @return The VALUE associated with the ID, or null if not found
     * @throws SQLException If read operation fails
     */
    public String read(String id) throws SQLException {
        String sql = "SELECT VALUE FROM store_context WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("VALUE");
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retryResetConnectionPool();
            return null;
        }
    }

    /**
     * Delete data from store_context table by ID
     * 
     * @param id The ID to delete
     * @return true if a record was deleted, false if ID not found
     * @throws SQLException If delete operation fails
     */
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM store_context WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            retryResetConnectionPool();
            return false;
        }
    }

    /**
     * Get all IDs from store_context table
     * 
     * @return List of all IDs
     * @throws SQLException If select operation fails
     */
    public java.util.List<String> selectAllIds() throws SQLException {
        java.util.List<String> ids = new java.util.ArrayList<>();
        String sql = "SELECT ID FROM store_context";
        try (Connection conn = dataSource.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getString("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            retryResetConnectionPool();
        }
        return ids;
    }

    private void retryResetConnectionPool() throws SQLException {
        closeConnection();
        initializeConnectionPool();
    }
}
