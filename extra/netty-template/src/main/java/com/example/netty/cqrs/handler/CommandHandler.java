package com.example.netty.cqrs.handler;

/**
 * Base interface for command handlers
 */
public interface CommandHandler<TCommand, TResult> {
    TResult handle(TCommand command);
}
