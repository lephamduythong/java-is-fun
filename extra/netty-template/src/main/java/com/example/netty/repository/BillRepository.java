package com.example.netty.repository;

import com.example.netty.repository.entity.Bill;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SQLite implementation of BillRepository
 */
public class BillRepository implements IBillRepository {
    
    private static final String DB_PATH = "jdbc:sqlite:src/main/resources/dbs/bills.db";
    private static BillRepository instance;
    
    private BillRepository() {
        initializeDatabase();
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized BillRepository getInstance() {
        if (instance == null) {
            instance = new BillRepository();
        }
        return instance;
    }
    
    /**
     * Initialize database and create table if not exists
     */
    private void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS bills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "bill_number TEXT NOT NULL UNIQUE, " +
                "customer_name TEXT NOT NULL, " +
                "amount REAL NOT NULL, " +
                "status TEXT NOT NULL, " +
                "created_at TEXT NOT NULL, " +
                "updated_at TEXT NOT NULL)";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Bills table initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
    
    /**
     * Get database connection
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_PATH);
    }
    
    @Override
    public Bill save(Bill bill) {
        String sql = "INSERT INTO bills (bill_number, customer_name, amount, status, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        
        LocalDateTime now = LocalDateTime.now();
        bill.setCreatedAt(now);
        bill.setUpdatedAt(now);
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bill.getBillNumber());
            pstmt.setString(2, bill.getCustomerName());
            pstmt.setBigDecimal(3, bill.getAmount());
            pstmt.setString(4, bill.getStatus());
            pstmt.setString(5, bill.getCreatedAt().toString());
            pstmt.setString(6, bill.getUpdatedAt().toString());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }
            
            // Get the last inserted row ID using SQLite specific function
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    bill.setId(rs.getLong(1));
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
            
            return bill;
        } catch (SQLException e) {
            System.err.println("Error saving bill: " + e.getMessage());
            throw new RuntimeException("Failed to save bill", e);
        }
    }
    
    @Override
    public Bill update(Bill bill) {
        String sql = "UPDATE bills " +
                "SET bill_number = ?, customer_name = ?, amount = ?, status = ?, updated_at = ? " +
                "WHERE id = ?";
        
        bill.setUpdatedAt(LocalDateTime.now());
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bill.getBillNumber());
            pstmt.setString(2, bill.getCustomerName());
            pstmt.setBigDecimal(3, bill.getAmount());
            pstmt.setString(4, bill.getStatus());
            pstmt.setString(5, bill.getUpdatedAt().toString());
            pstmt.setLong(6, bill.getId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new RuntimeException("Bill not found with id: " + bill.getId());
            }
            
            return bill;
        } catch (SQLException e) {
            System.err.println("Error updating bill: " + e.getMessage());
            throw new RuntimeException("Failed to update bill", e);
        }
    }
    
    @Override
    public Optional<Bill> findById(Long id) {
        String sql = "SELECT * FROM bills WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding bill by id: " + e.getMessage());
            throw new RuntimeException("Failed to find bill", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public Optional<Bill> findByBillNumber(String billNumber) {
        String sql = "SELECT * FROM bills WHERE bill_number = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, billNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding bill by number: " + e.getMessage());
            throw new RuntimeException("Failed to find bill", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Bill> findAll() {
        String sql = "SELECT * FROM bills ORDER BY created_at DESC";
        List<Bill> bills = new ArrayList<>();
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bills.add(mapResultSetToBill(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all bills: " + e.getMessage());
            throw new RuntimeException("Failed to find bills", e);
        }
        
        return bills;
    }
    
    @Override
    public List<Bill> findByStatus(String status) {
        String sql = "SELECT * FROM bills WHERE status = ? ORDER BY created_at DESC";
        List<Bill> bills = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding bills by status: " + e.getMessage());
            throw new RuntimeException("Failed to find bills", e);
        }
        
        return bills;
    }
    
    @Override
    public List<Bill> findByCustomerName(String customerName) {
        String sql = "SELECT * FROM bills WHERE customer_name LIKE ? ORDER BY created_at DESC";
        List<Bill> bills = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + customerName + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding bills by customer: " + e.getMessage());
            throw new RuntimeException("Failed to find bills", e);
        }
        
        return bills;
    }
    
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM bills WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting bill: " + e.getMessage());
            throw new RuntimeException("Failed to delete bill", e);
        }
    }
    
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM bills";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting bills: " + e.getMessage());
            throw new RuntimeException("Failed to count bills", e);
        }
        
        return 0;
    }
    
    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }
    
    /**
     * Helper method to map ResultSet to Bill object
     */
    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getLong("id"));
        bill.setBillNumber(rs.getString("bill_number"));
        bill.setCustomerName(rs.getString("customer_name"));
        bill.setAmount(rs.getBigDecimal("amount"));
        bill.setStatus(rs.getString("status"));
        bill.setCreatedAt(LocalDateTime.parse(rs.getString("created_at")));
        bill.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at")));
        return bill;
    }
}
