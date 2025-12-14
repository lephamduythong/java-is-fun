package com.example.netty.eventsourcing.handler;

import com.example.netty.eventsourcing.event.*;
import com.example.netty.eventsourcing.interf.IEventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Event handler that maintains statistics
 * Example of a projection/read model
 */
public class StatisticsEventHandler implements IEventHandler {
    
    private final Map<String, Integer> eventTypeCounts = new HashMap<>();
    private int totalEventsProcessed = 0;
    private int productsCreated = 0;
    private int productsDeleted = 0;

    @Override
    public void handle(DomainEvent event) {
        totalEventsProcessed++;
        
        String eventType = event.getEventType();
        eventTypeCounts.put(eventType, eventTypeCounts.getOrDefault(eventType, 0) + 1);
        
        if (event instanceof ProductCreatedEvent) {
            productsCreated++;
        } else if (event instanceof ProductDeletedEvent) {
            productsDeleted++;
        }
    }

    @Override
    public boolean canHandle(DomainEvent event) {
        return true; // Handle all events
    }

    // Getters for statistics
    
    public int getTotalEventsProcessed() {
        return totalEventsProcessed;
    }

    public int getProductsCreated() {
        return productsCreated;
    }

    public int getProductsDeleted() {
        return productsDeleted;
    }

    public int getActiveProducts() {
        return productsCreated - productsDeleted;
    }

    public Map<String, Integer> getEventTypeCounts() {
        return new HashMap<>(eventTypeCounts);
    }

    public void printStatistics() {
        System.out.println("\n=== Event Statistics ===");
        System.out.println("Total events processed: " + totalEventsProcessed);
        System.out.println("Products created: " + productsCreated);
        System.out.println("Products deleted: " + productsDeleted);
        System.out.println("Active products: " + getActiveProducts());
        System.out.println("\nEvent type breakdown:");
        eventTypeCounts.forEach((type, count) -> 
            System.out.println("  - " + type + ": " + count)
        );
        System.out.println("========================\n");
    }
}
