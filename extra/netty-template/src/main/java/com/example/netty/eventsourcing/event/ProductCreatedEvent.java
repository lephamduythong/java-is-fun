package com.example.netty.eventsourcing.event;

import java.math.BigDecimal;

/**
 * Event fired when a product is created
 */
public class ProductCreatedEvent extends DomainEvent {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;

    public ProductCreatedEvent(String aggregateId, long version, String name, 
                              String description, BigDecimal price, Integer quantity) {
        super(aggregateId, version);
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

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

    @Override
    public String getEventType() {
        return "ProductCreated";
    }

    @Override
    public String toString() {
        return "ProductCreatedEvent{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", " + super.toString() +
                '}';
    }
}
