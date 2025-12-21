package com.example.netty.cqrs.handler;

import com.example.netty.cqrs.command.CreateProductCommand;
import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.interf.ICommandHandler;
import com.example.netty.cqrs.interf.IProductRepository;

/**
 * Handler for CreateProductCommand
 */
public class CreateProductCommandHandler implements ICommandHandler<CreateProductCommand, Product> {
    
    private final IProductRepository repository;

    public CreateProductCommandHandler(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product handle(CreateProductCommand command) {
        // Validate command
        if (command.getName() == null || command.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (command.getPrice() == null || command.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }
        if (command.getQuantity() == null || command.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity must be non-negative");
        }

        // Create and save product
        Product product = new Product(
            command.getName(),
            command.getDescription(),
            command.getPrice(),
            command.getQuantity()
        );

        return repository.save(product);
    }
}
