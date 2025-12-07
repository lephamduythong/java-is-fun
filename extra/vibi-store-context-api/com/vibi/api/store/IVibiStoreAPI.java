package com.vibi.api.store;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IVibiStoreAPI {
    
    /**
     * Get current connection (thread-safe)
     * 
     * @return Connection object
     * @throws SQLException If connection is invalid
     */
    Connection getConnection() throws SQLException;
    
    /**
     * Close database connection
     */
    void closeConnection() throws SQLException;
    
    /**
     * Get full path to database file
     * 
     * @return Database path
     */
    String getDatabasePath();
    
    /**
     * Write data to store_context table
     * 
     * @param id    The ID value (primary key)
     * @param value The VALUE to store
     * @throws SQLException If write operation fails
     */
    void write(String id, String value) throws SQLException;
    
    /**
     * Read data from store_context table by ID
     * 
     * @param id The ID to look up
     * @return The VALUE associated with the ID, or null if not found
     * @throws SQLException If read operation fails
     */
    String read(String id) throws SQLException;
    
    /**
     * Delete data from store_context table by ID
     * 
     * @param id The ID to delete
     * @return true if a record was deleted, false if ID not found
     * @throws SQLException If delete operation fails
     */
    boolean delete(String id) throws SQLException;
    
    /**
     * Get all IDs from store_context table
     * 
     * @return List of all IDs
     * @throws SQLException If select operation fails
     */
    List<String> selectAllIds() throws SQLException;
}
