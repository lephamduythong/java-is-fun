package com.example.netty.cqrs.mediator;

import com.example.netty.cqrs.command.CreateProductCommand;
import com.example.netty.cqrs.command.DeleteProductCommand;
import com.example.netty.cqrs.command.UpdateProductCommand;
import com.example.netty.cqrs.domain.Product;
import com.example.netty.cqrs.handler.*;
import com.example.netty.cqrs.interf.ICommandHandler;
import com.example.netty.cqrs.interf.IQueryHandler;
import com.example.netty.cqrs.interf.IProductRepository;
import com.example.netty.cqrs.query.GetAllProductsQuery;
import com.example.netty.cqrs.query.GetProductByIdQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CQRS Mediator that routes commands and queries to their respective handlers
 */
public class CQRSMediator {
    
    private final Map<Class<?>, Object> commandHandlers = new HashMap<>();
    private final Map<Class<?>, Object> queryHandlers = new HashMap<>();

    public CQRSMediator(IProductRepository repository) {
        // Register command handlers
        registerCommandHandler(CreateProductCommand.class, new CreateProductCommandHandler(repository));
        registerCommandHandler(UpdateProductCommand.class, new UpdateProductCommandHandler(repository));
        registerCommandHandler(DeleteProductCommand.class, new DeleteProductCommandHandler(repository));

        // Register query handlers
        registerQueryHandler(GetProductByIdQuery.class, new GetProductByIdQueryHandler(repository));
        registerQueryHandler(GetAllProductsQuery.class, new GetAllProductsQueryHandler(repository));
    }

    /**
     * Register a command handler
     */
    public <TCommand, TResult> void registerCommandHandler(
            Class<TCommand> commandClass, 
            ICommandHandler<TCommand, TResult> handler) {
        commandHandlers.put(commandClass, handler);
    }

    /**
     * Register a query handler
     */
    public <TQuery, TResult> void registerQueryHandler(
            Class<TQuery> queryClass, 
            IQueryHandler<TQuery, TResult> handler) {
        queryHandlers.put(queryClass, handler);
    }

    /**
     * Send a command and get the result
     */
    @SuppressWarnings("unchecked")
    public <TCommand, TResult> TResult send(TCommand command) {
        ICommandHandler<TCommand, TResult> handler = 
            (ICommandHandler<TCommand, TResult>) commandHandlers.get(command.getClass());
        
        if (handler == null) {
            throw new IllegalArgumentException("No handler registered for command: " + command.getClass().getName());
        }
        
        return handler.handle(command);
    }

    /**
     * Send a query and get the result
     */
    @SuppressWarnings("unchecked")
    public <TQuery, TResult> TResult query(TQuery query) {
        IQueryHandler<TQuery, TResult> handler = 
            (IQueryHandler<TQuery, TResult>) queryHandlers.get(query.getClass());
        
        if (handler == null) {
            throw new IllegalArgumentException("No handler registered for query: " + query.getClass().getName());
        }
        
        return handler.handle(query);
    }

    // Convenience methods for common operations
    
    public Product createProduct(CreateProductCommand command) {
        return send(command);
    }

    public Product updateProduct(UpdateProductCommand command) {
        return send(command);
    }

    public Boolean deleteProduct(DeleteProductCommand command) {
        return send(command);
    }

    public Optional<Product> getProductById(GetProductByIdQuery query) {
        return query(query);
    }

    public List<Product> getAllProducts(GetAllProductsQuery query) {
        return query(query);
    }
}
