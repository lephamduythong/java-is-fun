package com.vibi.api.store;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class VibiStoreContextAPI { // WARNING: Change this name class for only 1 DB name only

    private static volatile VibiStoreContextAPI instance;
    private Connection connection;
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

        // Initialize connection
        initializeConnection();

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
     * Initialize connection to database
     */
    private void initializeConnection() throws SQLException {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create connection to SQLite
            String url = "jdbc:sqlite:" + dbPath;
            connection = DriverManager.getConnection(url);

        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }

    /**
     * Enable WAL (Write-Ahead Logging) mode for database
     */
    private void enableWALMode() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL;");
        }
    }

    /**
     * Create default table with ID and VALUE columns
     */
    private void createDefaultTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS store_context (" +
                    "ID TEXT PRIMARY KEY, " +
                    "VALUE TEXT)");
        }
    }

    /**
     * Get current connection (thread-safe)
     * 
     * @return Connection object
     * @throws SQLException If connection is invalid
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {

            if (connection == null || connection.isClosed()) {
                initializeConnection();
            }

        }
        return connection;
    }

    /**
     * Close database connection
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
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
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, value);
            pstmt.executeUpdate();
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
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("VALUE");
                }
                return null;
            }
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
        try (var pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
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
        try (var stmt = connection.createStatement();
                var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getString("ID"));
            }
        }
        return ids;
    }
}
