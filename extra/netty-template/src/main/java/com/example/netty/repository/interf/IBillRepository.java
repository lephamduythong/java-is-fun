package com.example.netty.repository.interf;

import com.example.netty.repository.entity.Bill;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Bill entity following Repository Pattern
 */
public interface IBillRepository {
    
    /**
     * Save a new bill to the database
     * @param bill Bill to save
     * @return Saved bill with generated ID
     */
    Bill save(Bill bill);
    
    /**
     * Update an existing bill
     * @param bill Bill to update
     * @return Updated bill
     */
    Bill update(Bill bill);
    
    /**
     * Find a bill by ID
     * @param id Bill ID
     * @return Optional containing bill if found
     */
    Optional<Bill> findById(Long id);
    
    /**
     * Find a bill by bill number
     * @param billNumber Bill number
     * @return Optional containing bill if found
     */
    Optional<Bill> findByBillNumber(String billNumber);
    
    /**
     * Find all bills
     * @return List of all bills
     */
    List<Bill> findAll();
    
    /**
     * Find bills by status
     * @param status Bill status (PENDING, PAID, CANCELLED)
     * @return List of bills with the specified status
     */
    List<Bill> findByStatus(String status);
    
    /**
     * Find bills by customer name
     * @param customerName Customer name
     * @return List of bills for the customer
     */
    List<Bill> findByCustomerName(String customerName);
    
    /**
     * Delete a bill by ID
     * @param id Bill ID
     * @return true if deleted, false if not found
     */
    boolean deleteById(Long id);
    
    /**
     * Count total number of bills
     * @return Total count
     */
    long count();
    
    /**
     * Check if a bill exists by ID
     * @param id Bill ID
     * @return true if exists
     */
    boolean existsById(Long id);
}
