package com.example.netty.cqrs.query;

/**
 * Query to get a product by ID
 */
public class GetProductByIdQuery {
    private final String id;

    public GetProductByIdQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
