package com.example.netty.cqrs.handler;

import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.query.GetProductByIdQuery;
import com.example.netty.cqrs.repository.ProductRepository;

import java.util.Optional;

/**
 * Handler for GetProductByIdQuery
 */
public class GetProductByIdQueryHandler implements QueryHandler<GetProductByIdQuery, Optional<Product>> {
    
    private final ProductRepository repository;

    public GetProductByIdQueryHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        if (query.getId() == null || query.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID is required");
        }
        
        return repository.findById(query.getId());
    }
}
