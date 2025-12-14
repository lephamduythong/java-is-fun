package com.example.netty.repository.main;

import com.example.netty.repository.BillRepository;
import com.example.netty.repository.entity.Bill;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Demo to test validation with negative amount
 */
public class ValidationDemoMain {
    
    public static void main(String[] args) {
        System.out.println("=== Bill Validation Demo ===\n");
        
        BillRepository repository = BillRepository.getInstance();
        
        // Test 1: Valid bill with positive amount
        System.out.println("Test 1: Creating bill with positive amount (1500000)");
        try {
            Bill validBill = new Bill();
            validBill.setBillNumber("VALID-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase().replaceAll("-", ""));
            validBill.setCustomerName("Nguyen Van A");
            validBill.setAmount(new BigDecimal("1500000"));
            validBill.setStatus("PENDING");
            
            Bill saved = repository.save(validBill);
            System.out.println("✅ SUCCESS: " + saved);
        } catch (Exception e) {
            System.out.println("❌ FAILED: " + e.getMessage());
        }
        
        // Test 2: Invalid bill with negative amount
        System.out.println("\nTest 2: Creating bill with negative amount (-1000)");
        try {
            Bill invalidBill = new Bill();
            invalidBill.setBillNumber("INVALID-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase().replaceAll("-", ""));
            invalidBill.setCustomerName("Tran Thi B");
            invalidBill.setAmount(new BigDecimal("-1000"));
            invalidBill.setStatus("PENDING");
            
            Bill saved = repository.save(invalidBill);
            System.out.println("❌ UNEXPECTED: Bill saved despite negative amount: " + saved);
        } catch (Exception e) {
            System.out.println("✅ VALIDATION CAUGHT: " + e.getMessage());
        }
        
        // Test 3: Valid bill with zero amount
        System.out.println("\nTest 3: Creating bill with zero amount (0)");
        try {
            Bill zeroBill = new Bill();
            zeroBill.setBillNumber("ZERO-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase().replaceAll("-", ""));
            zeroBill.setCustomerName("Le Van C");
            zeroBill.setAmount(BigDecimal.ZERO);
            zeroBill.setStatus("PENDING");
            
            Bill saved = repository.save(zeroBill);
            System.out.println("✅ SUCCESS: " + saved);
        } catch (Exception e) {
            System.out.println("❌ FAILED: " + e.getMessage());
        }
        
        // Test 4: Update existing bill with negative amount
        System.out.println("\nTest 4: Updating bill with negative amount");
        try {
            Bill existingBill = repository.findById(1L).orElse(null);
            if (existingBill != null) {
                System.out.println("Before update: " + existingBill);
                existingBill.setAmount(new BigDecimal("-5000"));
                repository.update(existingBill);
                System.out.println("❌ UNEXPECTED: Bill updated despite negative amount");
            } else {
                System.out.println("No bill found to update");
            }
        } catch (Exception e) {
            System.out.println("✅ VALIDATION CAUGHT: " + e.getMessage());
        }

        System.out.println("\n=== Validation Demo Completed ===");
    }
}
