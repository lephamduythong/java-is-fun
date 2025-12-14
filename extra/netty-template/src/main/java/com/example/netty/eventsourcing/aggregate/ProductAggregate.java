package com.example.netty.eventsourcing.aggregate;

import com.example.netty.eventsourcing.event.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Product Aggregate using Event Sourcing
 * State is rebuilt from events
 */
public class ProductAggregate extends AggregateRoot {
    
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductAggregate() {
        super();
    }

    // ========== Commands (Business Logic) ==========

    /**
     * Create a new product
     */
    public static ProductAggregate create(String name, String description, 
                                         BigDecimal price, Integer quantity) {
        ProductAggregate product = new ProductAggregate();
        String id = UUID.randomUUID().toString();
        product.setId(id);
        
        ProductCreatedEvent event = new ProductCreatedEvent(
            id, 1, name, description, price, quantity
        );
        product.applyChange(event);
        
        return product;
    }

    /**
     * Change product name
     */
    public void changeName(String newName) {
        if (deleted) {
            throw new IllegalStateException("Cannot modify deleted product");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (newName.equals(this.name)) {
            return; // No change
        }
        
        ProductNameChangedEvent event = new ProductNameChangedEvent(
            getId(), getVersion() + 1, this.name, newName
        );
        applyChange(event);
    }

    /**
     * Change product price
     */
    public void changePrice(BigDecimal newPrice) {
        if (deleted) {
            throw new IllegalStateException("Cannot modify deleted product");
        }
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
        if (newPrice.compareTo(this.price) == 0) {
            return; // No change
        }
        
        ProductPriceChangedEvent event = new ProductPriceChangedEvent(
            getId(), getVersion() + 1, this.price, newPrice
        );
        applyChange(event);
    }

    /**
     * Adjust quantity
     */
    public void adjustQuantity(Integer adjustment) {
        if (deleted) {
            throw new IllegalStateException("Cannot modify deleted product");
        }
        if (adjustment == 0) {
            return; // No change
        }
        
        Integer newQuantity = this.quantity + adjustment;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        ProductQuantityAdjustedEvent event = new ProductQuantityAdjustedEvent(
            getId(), getVersion() + 1, this.quantity, newQuantity, adjustment
        );
        applyChange(event);
    }

    /**
     * Delete product
     */
    public void delete(String reason) {
        if (deleted) {
            throw new IllegalStateException("Product already deleted");
        }
        
        ProductDeletedEvent event = new ProductDeletedEvent(
            getId(), getVersion() + 1, reason
        );
        applyChange(event);
    }

    // ========== Event Handlers (State Changes) ==========

    @Override
    protected void applyEventToAggregate(DomainEvent event) {
        if (event instanceof ProductCreatedEvent) {
            apply((ProductCreatedEvent) event);
        } else if (event instanceof ProductNameChangedEvent) {
            apply((ProductNameChangedEvent) event);
        } else if (event instanceof ProductPriceChangedEvent) {
            apply((ProductPriceChangedEvent) event);
        } else if (event instanceof ProductQuantityAdjustedEvent) {
            apply((ProductQuantityAdjustedEvent) event);
        } else if (event instanceof ProductDeletedEvent) {
            apply((ProductDeletedEvent) event);
        }
    }

    private void apply(ProductCreatedEvent event) {
        this.name = event.getName();
        this.description = event.getDescription();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
        this.createdAt = event.getOccurredOn();
        this.updatedAt = event.getOccurredOn();
        this.deleted = false;
    }

    private void apply(ProductNameChangedEvent event) {
        this.name = event.getNewName();
        this.updatedAt = event.getOccurredOn();
    }

    private void apply(ProductPriceChangedEvent event) {
        this.price = event.getNewPrice();
        this.updatedAt = event.getOccurredOn();
    }

    private void apply(ProductQuantityAdjustedEvent event) {
        this.quantity = event.getNewQuantity();
        this.updatedAt = event.getOccurredOn();
    }

    private void apply(ProductDeletedEvent event) {
        this.deleted = true;
        this.updatedAt = event.getOccurredOn();
    }

    // ========== Getters ==========

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "ProductAggregate{" +
                "id='" + getId() + '\'' +
                ", version=" + getVersion() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
