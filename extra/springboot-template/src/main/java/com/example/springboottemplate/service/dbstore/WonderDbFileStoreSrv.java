package com.example.springboottemplate.service.dbstore;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WonderDbFileStoreSrv { // WARNING: Change this name class for only 1 DB name only

    private static final Logger _logger = LoggerFactory.getLogger("MY_SYSTEM");

    private static volatile WonderDbFileStoreSrv instance;
    private HikariDataSource dataSource;
    private final String dbPath;
    private static final String BASE_DIR = "E:/CODING/DBs/"; // WARNING: Change this BASE_DIR

    // Private constructor to ensure singleton pattern
    private WonderDbFileStoreSrv() throws SQLException {
        _logger.debug("[1] Starting WonderDbStoreSrv constructor");
        // Use class name as database name
        String databaseName = this.getClass().getSimpleName() + ".db";
        // Create directory if it doesn't exist
        _logger.debug("[2] Creating directory if not exists: {}", BASE_DIR);
        File directory = new File(BASE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Full path to database file
        this.dbPath = BASE_DIR + databaseName;
        _logger.debug("[3] Database path: {}", this.dbPath);
        File dbFile = new File(this.dbPath);
        boolean isNewDatabase = !dbFile.exists();
        _logger.debug("[4] Is new database: {}", isNewDatabase);

        // Initialize HikariCP connection pool
        _logger.debug("[5] Initializing connection pool");
        initializeConnectionPool();

        // If new database, enable WAL mode
        if (isNewDatabase) {
            _logger.debug("[6] Enabling WAL mode for new database");
            enableWALMode();
        }

        // Create default table
        _logger.debug("[7] Creating default table");
        createDefaultTable();
        _logger.debug("[8] WonderDbStoreSrv constructor completed");
    }

    private WonderDbFileStoreSrv(String customPath) throws SQLException {
        _logger.debug("[9] Starting WonderDbStoreSrv constructor with custom path: {}", customPath);
        // Use custom path for database file
        this.dbPath = customPath;
        File dbFile = new File(this.dbPath);
        boolean isNewDatabase = !dbFile.exists();
        _logger.debug("[10] Is new database: {}", isNewDatabase);

        // Initialize HikariCP connection pool
        _logger.debug("[11] Initializing connection pool");
        initializeConnectionPool();

        // If new database, enable WAL mode
        if (isNewDatabase) {
            _logger.debug("[12] Enabling WAL mode for new database");
            enableWALMode();
        }

        // Create default table
        _logger.debug("[13] Creating default table");
        createDefaultTable();
        _logger.debug("[14] WonderDbStoreSrv constructor with custom path completed");
    }

    /**
     * Get instance of VibiStoreContextAPI (thread-safe double-checked locking)
     * Database name will automatically be set to the class name (e.g., "VibiStoreContextAPI.db")
     * 
     * @return Unique instance of the class
     * @throws SQLException If error occurs during database initialization
     */
    public static WonderDbFileStoreSrv getInstance() throws SQLException {
        _logger.debug("[15] getInstance() called");
        if (instance == null) {
            synchronized (WonderDbFileStoreSrv.class) {
                if (instance == null) {
                    _logger.debug("[16] Creating new WonderDbStoreSrv instance");
                    instance = new WonderDbFileStoreSrv();
                }
            }
        }
        _logger.debug("[17] Returning WonderDbStoreSrv instance");
        return instance;
    }

    /**
     * Get instance of VibiStoreContextAPI with custom database path (thread-safe double-checked locking)
     * 
     * @param customPath Custom path for the database file
     * @return Unique instance of the class
     * @throws SQLException If error occurs during database initialization
     */
    public static WonderDbFileStoreSrv getInstance(String customPath) throws SQLException {
        _logger.debug("[18] getInstance(customPath) called with path: {}", customPath);
        if (instance == null) {
            synchronized (WonderDbFileStoreSrv.class) {
                if (instance == null) {
                    _logger.debug("[19] Creating new WonderDbStoreSrv instance with custom path");
                    instance = new WonderDbFileStoreSrv(customPath);
                }
            }
        }
        _logger.debug("[20] Returning WonderDbStoreSrv instance");
        return instance;
    }

    /**
     * Initialize HikariCP connection pool
     */
    private void initializeConnectionPool() throws SQLException {
        _logger.debug("[21] Initializing HikariCP connection pool");
        try {
            // Load SQLite JDBC driver
            _logger.debug("[22] Loading SQLite JDBC driver");
            Class.forName("org.sqlite.JDBC");

            // Configure HikariCP
            _logger.debug("[23] Configuring HikariCP with dbPath: {}", dbPath);
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
            _logger.debug("[24] Creating HikariDataSource");
            dataSource = new HikariDataSource(config);
            _logger.debug("[25] HikariCP connection pool initialized successfully");

        } catch (ClassNotFoundException e) {
            _logger.error("[26] SQLite JDBC driver not found", e);
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }

    /**
     * Enable WAL (Write-Ahead Logging) mode for database
     */
    private void enableWALMode() throws SQLException {
        _logger.debug("[27] Enabling WAL mode");
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL;");
            _logger.debug("[28] WAL mode enabled successfully");
        }
    }

    /**
     * Create default table with ID and VALUE columns
     */
    private void createDefaultTable() throws SQLException {
        _logger.debug("[29] Creating default table 'store_context'");
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS store_context (" +
                    "ID TEXT PRIMARY KEY, " +
                    "VALUE TEXT)");
            _logger.debug("[30] Default table created successfully");
        }
    }

    /**
     * Get current connection from pool (thread-safe)
     * 
     * @return Connection object from pool
     * @throws SQLException If connection pool is closed or invalid
     */
    public synchronized Connection getConnection() throws SQLException {
        _logger.debug("[31] Getting connection from pool");
        if (dataSource == null || dataSource.isClosed()) {
            _logger.error("[32] Connection pool is closed");
            throw new SQLException("Connection pool is closed");
        }
        _logger.debug("[33] Connection retrieved successfully");
        return dataSource.getConnection();
    }

    /**
     * Close connection pool and all connections
     */
    public synchronized void closeConnection() {
        _logger.debug("[34] Closing connection pool");
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            _logger.debug("[35] Connection pool closed successfully");
        } else {
            _logger.debug("[36] Connection pool already closed or null");
        }
    }

    /**
     * Get full path to database file
     * 
     * @return Database path
     */
    public synchronized String getDatabasePath() {
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
        _logger.debug("[37] Writing data - ID: {}, VALUE: {}", id, value);
        String sql = "INSERT OR REPLACE INTO store_context (ID, VALUE) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, value);
            pstmt.executeUpdate();
            _logger.debug("[38] Data written successfully");
        } catch (SQLException e) {
            _logger.error("[39] Error writing data", e);
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
        _logger.debug("[40] Reading data for ID: {}", id);
        String sql = "SELECT VALUE FROM store_context WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
            var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String value = rs.getString("VALUE");
                    _logger.debug("[41] Data found for ID: {}", id);
                    return value;
                }
                _logger.debug("[42] No data found for ID: {}", id);
                return null;
            }
        } catch (SQLException e) {
            _logger.error("[43] Error reading data for ID: {}", id, e);
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
        _logger.debug("[44] Deleting data for ID: {}", id);
        String sql = "DELETE FROM store_context WHERE ID = ?";
        try (Connection conn = dataSource.getConnection();
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                _logger.debug("[45] Data deleted successfully for ID: {}", id);
            } else {
                _logger.debug("[46] No data found to delete for ID: {}", id);
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            _logger.error("[47] Error deleting data for ID: {}", id, e);
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
        _logger.debug("[48] Selecting all IDs from store_context");
        java.util.List<String> ids = new java.util.ArrayList<>();
        String sql = "SELECT ID FROM store_context";
        try (Connection conn = dataSource.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getString("ID"));
            }
            _logger.debug("[49] Retrieved {} IDs from store_context", ids.size());
        } catch (SQLException e) {
            _logger.error("[50] Error selecting all IDs", e);
            e.printStackTrace();
            retryResetConnectionPool();
        }
        return ids;
    }

    private void retryResetConnectionPool() throws SQLException {
        _logger.warn("[51] Retrying connection pool reset");
        closeConnection();
        initializeConnectionPool();
        _logger.debug("[52] Connection pool reset completed");
    }
}
