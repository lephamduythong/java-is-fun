package com.example.netty.cqrs.repository;

import com.example.netty.cqrs.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of ProductRepository
 */
public class InMemoryProductRepository implements ProductRepository {
    
    private final Map<String, Product> storage = new ConcurrentHashMap<>();
    
    @Override
    public Product save(Product product) {
        storage.put(product.getId(), product);
        return product;
    }
    
    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }
    
    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
    
    @Override
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }
}
