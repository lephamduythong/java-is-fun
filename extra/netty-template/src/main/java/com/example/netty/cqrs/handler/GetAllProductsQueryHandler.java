package com.example.netty.cqrs.handler;

import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.interf.IQueryHandler;
import com.example.netty.cqrs.interf.IProductRepository;
import com.example.netty.cqrs.query.GetAllProductsQuery;

import java.util.List;

/**
 * Handler for GetAllProductsQuery
 */
public class GetAllProductsQueryHandler implements IQueryHandler<GetAllProductsQuery, List<Product>> {
    
    private final IProductRepository repository;

    public GetAllProductsQueryHandler(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return repository.findAll();
    }
}
