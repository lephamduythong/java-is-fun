package com.example.netty.cqrs.handler;

/**
 * Base interface for query handlers
 */
public interface QueryHandler<TQuery, TResult> {
    TResult handle(TQuery query);
}
