package com.example.netty.cqrs.command;

/**
 * Command to delete a product
 */
public class DeleteProductCommand {
    private final String id;

    public DeleteProductCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
