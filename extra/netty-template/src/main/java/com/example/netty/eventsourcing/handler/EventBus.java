package com.example.netty.eventsourcing.handler;

import com.example.netty.eventsourcing.event.DomainEvent;
import com.example.netty.eventsourcing.interf.IEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Event Bus for publishing events to multiple handlers
 * Implements Observer pattern
 */
public class EventBus {
    
    private final List<IEventHandler> handlers = new ArrayList<>();

    /**
     * Register an event handler
     */
    public void register(IEventHandler handler) {
        handlers.add(handler);
    }

    /**
     * Unregister an event handler
     */
    public void unregister(IEventHandler handler) {
        handlers.remove(handler);
    }

    /**
     * Publish event to all registered handlers
     */
    public void publish(DomainEvent event) {
        for (IEventHandler handler : handlers) {
            if (handler.canHandle(event)) {
                try {
                    handler.handle(event);
                } catch (Exception e) {
                    System.err.println("Error handling event: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Publish multiple events
     */
    public void publishAll(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            publish(event);
        }
    }
}
