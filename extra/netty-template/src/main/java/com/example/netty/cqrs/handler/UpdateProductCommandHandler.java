package com.example.netty.cqrs.handler;

import com.example.netty.cqrs.command.UpdateProductCommand;
import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.interf.ICommandHandler;
import com.example.netty.cqrs.interf.IProductRepository;

/**
 * Handler for UpdateProductCommand
 */
public class UpdateProductCommandHandler implements ICommandHandler<UpdateProductCommand, Product> {
    
    private final IProductRepository repository;

    public UpdateProductCommandHandler(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product handle(UpdateProductCommand command) {
        // Validate command
        if (command.getId() == null || command.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID is required");
        }

        // Find existing product
        Product product = repository.findById(command.getId())
            .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + command.getId()));

        // Update fields
        if (command.getName() != null && !command.getName().trim().isEmpty()) {
            product.setName(command.getName());
        }
        if (command.getDescription() != null) {
            product.setDescription(command.getDescription());
        }
        if (command.getPrice() != null && command.getPrice().doubleValue() >= 0) {
            product.setPrice(command.getPrice());
        }
        if (command.getQuantity() != null && command.getQuantity() >= 0) {
            product.setQuantity(command.getQuantity());
        }

        return repository.save(product);
    }
}
