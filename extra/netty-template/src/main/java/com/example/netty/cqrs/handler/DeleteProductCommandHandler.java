package com.example.netty.cqrs.handler;

import com.example.netty.cqrs.command.DeleteProductCommand;
import com.example.netty.cqrs.repository.ProductRepository;

/**
 * Handler for DeleteProductCommand
 */
public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, Boolean> {
    
    private final ProductRepository repository;

    public DeleteProductCommandHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean handle(DeleteProductCommand command) {
        // Validate command
        if (command.getId() == null || command.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID is required");
        }

        // Check if product exists
        if (!repository.existsById(command.getId())) {
            throw new IllegalArgumentException("Product not found with ID: " + command.getId());
        }

        return repository.deleteById(command.getId());
    }
}
