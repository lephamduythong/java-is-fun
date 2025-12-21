package com.example.netty.eventsourcing.interf;

import com.example.netty.eventsourcing.event.DomainEvent;

/**
 * Interface for event handlers (projections, notifications, etc.)
 */
public interface IEventHandler {
    
    /**
     * Handle a domain event
     */
    void handle(DomainEvent event);
    
    /**
     * Check if this handler can handle the event
     */
    boolean canHandle(DomainEvent event);
}
