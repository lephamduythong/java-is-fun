package com.example.netty.repository;

import com.example.netty.repository.entity.Bill;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Demo class to demonstrate CRUD operations using BillRepository
 */
public class BillRepositoryDemoMain {
    
    public static void main(String[] args) {
        System.out.println("=== Bill Repository CRUD Demo ===\n");
        
        IBillRepository repository = BillRepository.getInstance();

        // CREATE - Insert new bills
        System.out.println("=== CREATE Operation ===");
        Bill bill1 = new Bill();
        bill1.setBillNumber("BILL-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase().replaceAll("-", ""));
        bill1.setCustomerName("Nguyen Van A");
        bill1.setAmount(new BigDecimal("1500000"));
        bill1.setStatus("PENDING");
        bill1.setCreatedBy("Nhan vien 1");
        
        Bill bill2 = new Bill();
        bill2.setBillNumber("BILL-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase().replaceAll("-", ""));
        bill2.setCustomerName("Tran Thi B");
        bill2.setAmount(new BigDecimal("2500000"));
        bill2.setStatus("PAID");
        bill2.setCreatedBy("Nhan vien 2");
        
        Bill bill3 = new Bill();
        bill3.setBillNumber("BILL-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase().replaceAll("-", ""));
        bill3.setCustomerName("Le Van C");
        bill3.setAmount(new BigDecimal("3200000"));
        bill3.setStatus("PENDING");
        bill3.setCreatedBy("Nhan vien 3");
        
        bill1 = repository.save(bill1);
        System.out.println("Created: " + bill1);
        
        bill2 = repository.save(bill2);
        System.out.println("Created: " + bill2);
        
        bill3 = repository.save(bill3);
        System.out.println("Created: " + bill3);
        
        System.out.println("\nTotal bills: " + repository.count() + "\n");
        
        // READ - Find operations
        System.out.println("=== READ Operation ===");
        
        // Find by ID
        System.out.println("Find by ID (1):");
        Optional<Bill> foundById = repository.findById(1L);
        foundById.ifPresent(System.out::println);
        
        // Find by bill number
        System.out.println("\nFind by Bill Number (BILL-002):");
        Optional<Bill> foundByNumber = repository.findByBillNumber("BILL-002");
        foundByNumber.ifPresent(System.out::println);
        
        // Find all
        System.out.println("\nFind All:");
        List<Bill> allBills = repository.findAll();
        allBills.forEach(System.out::println);
        
        // Find by status
        System.out.println("\nFind by Status (PENDING):");
        List<Bill> pendingBills = repository.findByStatus("PENDING");
        pendingBills.forEach(System.out::println);
        
        // Find by customer name
        System.out.println("\nFind by Customer Name (Nguyen):");
        List<Bill> customerBills = repository.findByCustomerName("Nguyen");
        customerBills.forEach(System.out::println);
        
        // UPDATE - Modify existing bill
        System.out.println("\n=== UPDATE Operation ===");
        if (foundById.isPresent()) {
            Bill billToUpdate = foundById.get();
            System.out.println("Before update: " + billToUpdate);
            
            billToUpdate.setStatus("PAID");
            billToUpdate.setAmount(new BigDecimal("1800000"));
            repository.update(billToUpdate);
            
            Optional<Bill> updatedBill = repository.findById(billToUpdate.getId());
            System.out.println("After update:  " + updatedBill.orElse(null));
        }
        
        // DELETE - Remove a bill
        System.out.println("\n=== DELETE Operation ===");
        System.out.println("Before delete - Total bills: " + repository.count());
        
        boolean deleted = repository.deleteById(2L);
        System.out.println("Deleted bill with ID 2: " + deleted);
        System.out.println("After delete - Total bills: " + repository.count());
        
        // Check existence
        System.out.println("\n=== EXISTS Operation ===");
        System.out.println("Bill ID 1 exists: " + repository.existsById(1L));
        System.out.println("Bill ID 2 exists: " + repository.existsById(2L));
        System.out.println("Bill ID 3 exists: " + repository.existsById(3L));
        
        // Final state
        System.out.println("\n=== Final State ===");
        System.out.println("Remaining bills:");
        repository.findAll().forEach(System.out::println);
        System.out.println("\nTotal: " + repository.count() + " bills");
        
        System.out.println("\n=== Demo Completed ===");
    }
}
