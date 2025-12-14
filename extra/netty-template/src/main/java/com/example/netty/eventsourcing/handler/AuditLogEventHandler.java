package com.example.netty.eventsourcing.handler;

import com.example.netty.eventsourcing.event.*;
import com.example.netty.eventsourcing.interf.IEventHandler;

/**
 * Event handler that logs all events
 * Can be used for auditing or debugging
 */
public class AuditLogEventHandler implements IEventHandler {

    @Override
    public void handle(DomainEvent event) {
        System.out.println("[AUDIT] " + event.getOccurredOn() + 
                         " | " + event.getEventType() + 
                         " | Aggregate: " + event.getAggregateId() +
                         " | Version: " + event.getVersion());
        
        if (event instanceof ProductCreatedEvent) {
            ProductCreatedEvent e = (ProductCreatedEvent) event;
            System.out.println("        → Product created: " + e.getName() + 
                             " at $" + e.getPrice());
        } else if (event instanceof ProductNameChangedEvent) {
            ProductNameChangedEvent e = (ProductNameChangedEvent) event;
            System.out.println("        → Name changed: '" + e.getOldName() + 
                             "' → '" + e.getNewName() + "'");
        } else if (event instanceof ProductPriceChangedEvent) {
            ProductPriceChangedEvent e = (ProductPriceChangedEvent) event;
            System.out.println("        → Price changed: $" + e.getOldPrice() + 
                             " → $" + e.getNewPrice());
        } else if (event instanceof ProductQuantityAdjustedEvent) {
            ProductQuantityAdjustedEvent e = (ProductQuantityAdjustedEvent) event;
            System.out.println("        → Quantity adjusted: " + e.getOldQuantity() + 
                             " → " + e.getNewQuantity() + 
                             " (adjustment: " + e.getAdjustment() + ")");
        } else if (event instanceof ProductDeletedEvent) {
            ProductDeletedEvent e = (ProductDeletedEvent) event;
            System.out.println("        → Product deleted. Reason: " + e.getReason());
        }
    }

    @Override
    public boolean canHandle(DomainEvent event) {
        return true; // Handle all events
    }
}
