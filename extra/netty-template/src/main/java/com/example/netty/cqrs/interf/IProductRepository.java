package com.example.netty.cqrs.interf;

import com.example.netty.cqrs.domain.Product;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product entity
 */
public interface IProductRepository {
    
    /**
     * Save a product (create or update)
     */
    Product save(Product product);
    
    /**
     * Find product by ID
     */
    Optional<Product> findById(String id);
    
    /**
     * Find all products
     */
    List<Product> findAll();
    
    /**
     * Delete product by ID
     */
    boolean deleteById(String id);
    
    /**
     * Check if product exists by ID
     */
    boolean existsById(String id);
}
