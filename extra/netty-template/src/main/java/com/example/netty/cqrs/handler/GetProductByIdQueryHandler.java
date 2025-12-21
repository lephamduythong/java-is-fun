package com.example.netty.cqrs.handler;

import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.interf.IQueryHandler;
import com.example.netty.cqrs.interf.IProductRepository;
import com.example.netty.cqrs.query.GetProductByIdQuery;

import java.util.Optional;

/**
 * Handler for GetProductByIdQuery
 */
public class GetProductByIdQueryHandler implements IQueryHandler<GetProductByIdQuery, Optional<Product>> {
    
    private final IProductRepository repository;

    public GetProductByIdQueryHandler(IProductRepository repository) {
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
