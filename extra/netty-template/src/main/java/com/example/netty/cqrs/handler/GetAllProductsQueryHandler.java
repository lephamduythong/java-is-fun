package com.example.netty.cqrs.handler;

import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.query.GetAllProductsQuery;
import com.example.netty.cqrs.repository.ProductRepository;

import java.util.List;

/**
 * Handler for GetAllProductsQuery
 */
public class GetAllProductsQueryHandler implements QueryHandler<GetAllProductsQuery, List<Product>> {
    
    private final ProductRepository repository;

    public GetAllProductsQueryHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return repository.findAll();
    }
}
