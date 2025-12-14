package com.example.netty.cqrs;

import com.example.netty.cqrs.command.CreateProductCommand;
import com.example.netty.cqrs.command.DeleteProductCommand;
import com.example.netty.cqrs.command.UpdateProductCommand;
import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.mediator.CQRSMediator;
import com.example.netty.cqrs.query.GetAllProductsQuery;
import com.example.netty.cqrs.query.GetProductByIdQuery;
import com.example.netty.cqrs.repository.InMemoryProductRepository;
import com.example.netty.cqrs.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Demo application for CQRS pattern with Repository
 * Demonstrates Create, Read, Update, Delete operations
 */
public class CQRSDemoMain {

    public static void main(String[] args) {
        System.out.println("=== CQRS Pattern Demo with Repository ===\n");

        // Initialize repository and mediator
        ProductRepository repository = new InMemoryProductRepository();
        CQRSMediator mediator = new CQRSMediator(repository);

        // 1. CREATE - Create new products
        System.out.println("1. CREATE Operations:");
        System.out.println("---------------------");
        
        Product laptop = mediator.createProduct(new CreateProductCommand(
            "Laptop Dell XPS 15",
            "High-performance laptop with 16GB RAM",
            new BigDecimal("1299.99"),
            10
        ));
        System.out.println("Created: " + laptop);

        Product mouse = mediator.createProduct(new CreateProductCommand(
            "Logitech MX Master 3",
            "Wireless mouse for professionals",
            new BigDecimal("99.99"),
            25
        ));
        System.out.println("Created: " + mouse);

        Product keyboard = mediator.createProduct(new CreateProductCommand(
            "Keychron K2",
            "Mechanical keyboard with RGB lighting",
            new BigDecimal("89.99"),
            15
        ));
        System.out.println("Created: " + keyboard);

        System.out.println();

        // 2. READ - Get all products
        System.out.println("2. READ All Products:");
        System.out.println("---------------------");
        
        List<Product> allProducts = mediator.getAllProducts(new GetAllProductsQuery());
        System.out.println("Total products: " + allProducts.size());
        allProducts.forEach(p -> System.out.println("  - " + p.getName() + " ($" + p.getPrice() + ")"));
        
        System.out.println();

        // 3. READ - Get product by ID
        System.out.println("3. READ Product by ID:");
        System.out.println("---------------------");
        
        Optional<Product> foundProduct = mediator.getProductById(new GetProductByIdQuery(laptop.getId()));
        if (foundProduct.isPresent()) {
            System.out.println("Found product: " + foundProduct.get());
        } else {
            System.out.println("Product not found");
        }
        
        System.out.println();

        // 4. UPDATE - Update product
        System.out.println("4. UPDATE Product:");
        System.out.println("---------------------");
        
        System.out.println("Before update: " + laptop);
        
        Product updatedLaptop = mediator.updateProduct(new UpdateProductCommand(
            laptop.getId(),
            "Laptop Dell XPS 15 (2024)",
            "High-performance laptop with 32GB RAM and RTX 4060",
            new BigDecimal("1499.99"),
            8
        ));
        
        System.out.println("After update:  " + updatedLaptop);
        
        System.out.println();

        // 5. DELETE - Delete product
        System.out.println("5. DELETE Product:");
        System.out.println("---------------------");
        
        System.out.println("Products before delete: " + mediator.getAllProducts(new GetAllProductsQuery()).size());
        
        Boolean deleted = mediator.deleteProduct(new DeleteProductCommand(keyboard.getId()));
        System.out.println("Deleted successfully: " + deleted);
        
        System.out.println("Products after delete: " + mediator.getAllProducts(new GetAllProductsQuery()).size());
        
        System.out.println();

        // 6. Final state
        System.out.println("6. Final Product List:");
        System.out.println("---------------------");
        
        List<Product> finalProducts = mediator.getAllProducts(new GetAllProductsQuery());
        finalProducts.forEach(p -> {
            System.out.println("  ID: " + p.getId());
            System.out.println("  Name: " + p.getName());
            System.out.println("  Description: " + p.getDescription());
            System.out.println("  Price: $" + p.getPrice());
            System.out.println("  Quantity: " + p.getQuantity());
            System.out.println("  Created: " + p.getCreatedAt());
            System.out.println("  Updated: " + p.getUpdatedAt());
            System.out.println();
        });

        // 7. Test validation
        System.out.println("7. Validation Tests:");
        System.out.println("---------------------");
        
        try {
            mediator.createProduct(new CreateProductCommand(
                "",  // Empty name - should fail
                "Test",
                new BigDecimal("10.00"),
                5
            ));
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Validation passed: " + e.getMessage());
        }

        try {
            mediator.updateProduct(new UpdateProductCommand(
                "non-existent-id",  // Non-existent ID - should fail
                "Test",
                "Test",
                new BigDecimal("10.00"),
                5
            ));
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Validation passed: " + e.getMessage());
        }

        try {
            mediator.deleteProduct(new DeleteProductCommand("non-existent-id"));
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Validation passed: " + e.getMessage());
        }

        System.out.println("\n=== CQRS Demo Complete ===");
    }
}
