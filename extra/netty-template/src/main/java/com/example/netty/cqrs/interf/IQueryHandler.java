package com.example.netty.cqrs.interf;

/**
 * Base interface for query handlers
 */
public interface IQueryHandler<TQuery, TResult> {
    TResult handle(TQuery query);
}
