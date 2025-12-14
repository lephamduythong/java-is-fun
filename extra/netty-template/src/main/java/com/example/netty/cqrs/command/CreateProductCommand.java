package com.example.netty.cqrs.command;

import java.math.BigDecimal;

/**
 * Command to create a new product
 */
public class CreateProductCommand {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;

    public CreateProductCommand(String name, String description, BigDecimal price, Integer quantity) {
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
}
