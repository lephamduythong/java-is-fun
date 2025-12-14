package com.example.netty.cqrs.command;

import java.math.BigDecimal;

/**
 * Command to update an existing product
 */
public class UpdateProductCommand {
    private final String id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;

    public UpdateProductCommand(String id, String name, String description, BigDecimal price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
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
}
