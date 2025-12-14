package com.example.netty.repository.entity;

import com.example.netty.repository.validation.NonNegative;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing a Bill in the database
 */
public class Bill {
    private Long id;
    private String billNumber;
    private String customerName;
    
    @NonNegative(message = "Amount must be non-negative")
    private BigDecimal amount;
    
    private String status; // PENDING, PAID, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public Bill() {
    }

    public Bill(Long id, String billNumber, String customerName, BigDecimal amount, String status, 
                LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.billNumber = billNumber;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return 
        "Bill{" +
            "id=" + id +
            ", billNumber='" + billNumber + '\'' +
            ", customerName='" + customerName + '\'' +
            ", amount=" + amount +
            ", status='" + status + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
        '}';
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
