package com.example.netty.cqrs.interf;

/**
 * Base interface for command handlers
 */
public interface ICommandHandler<TCommand, TResult> {
    TResult handle(TCommand command);
}
