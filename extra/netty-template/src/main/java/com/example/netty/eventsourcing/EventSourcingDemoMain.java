package com.example.netty.eventsourcing;

import com.example.netty.eventsourcing.aggregate.ProductAggregate;
import com.example.netty.eventsourcing.event.DomainEvent;
import com.example.netty.eventsourcing.handler.AuditLogEventHandler;
import com.example.netty.eventsourcing.handler.EventBus;
import com.example.netty.eventsourcing.handler.StatisticsEventHandler;
import com.example.netty.eventsourcing.interf.IEventStore;
import com.example.netty.eventsourcing.repository.EventSourcedProductRepository;
import com.example.netty.eventsourcing.store.InMemoryEventStore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Demo application showing Event Sourcing in action
 * Demonstrates:
 * - Creating aggregates and storing events
 * - Rebuilding state from events
 * - Time travel (viewing state at different versions)
 * - Event handlers and projections
 * - Audit trail
 */
public class EventSourcingDemoMain {

    public static void main(String[] args) {
        System.out.println("=== Event Sourcing Pattern Demo ===\n");

        // Setup infrastructure
        IEventStore eventStore = new InMemoryEventStore(); 
        EventSourcedProductRepository repository = new EventSourcedProductRepository(eventStore);
        
        // Setup event bus and handlers (as a mediator)
        EventBus eventBus = new EventBus();
        AuditLogEventHandler auditHandler = new AuditLogEventHandler();
        StatisticsEventHandler statsHandler = new StatisticsEventHandler();
        
        // Add external handlers
        eventBus.register(auditHandler);
        eventBus.register(statsHandler);

        System.out.println("=== 1. Creating Products ===\n");
        
        // Create first product
        ProductAggregate laptop = ProductAggregate.create(
            "MacBook Pro 16\"",
            "High-end laptop for professionals",
            new BigDecimal("2499.99"),
            5
        );
        repository.save(laptop);
        eventBus.publishAll(eventStore.getEventsForAggregate(laptop.getId()));
        
        System.out.println("Created laptop: " + laptop);
        System.out.println();

        // Create second product
        ProductAggregate mouse = ProductAggregate.create(
            "Magic Mouse",
            "Wireless mouse",
            new BigDecimal("79.99"),
            20
        );
        repository.save(mouse);
        eventBus.publishAll(eventStore.getEventsForAggregate(mouse.getId()));
        
        System.out.println("Created mouse: " + mouse);
        System.out.println();

        System.out.println("\n=== 2. Modifying Products ===\n");
        
        // Make changes to laptop
        laptop.changeName("MacBook Pro 16\" M3");
        laptop.changePrice(new BigDecimal("2799.99"));
        laptop.adjustQuantity(-2); // Sold 2 units
        repository.save(laptop);
        eventBus.publishAll(laptop.getUncommittedEvents());
        
        System.out.println("Updated laptop: " + laptop);
        System.out.println();

        // Make changes to mouse
        mouse.changePrice(new BigDecimal("69.99")); // Price drop
        mouse.adjustQuantity(10); // Restocked
        repository.save(mouse);
        eventBus.publishAll(mouse.getUncommittedEvents());
        
        System.out.println("Updated mouse: " + mouse);
        System.out.println();

        System.out.println("\n=== 3. Event History ===\n");
        
        List<DomainEvent> laptopHistory = repository.getHistory(laptop.getId());
        System.out.println("Laptop event history (" + laptopHistory.size() + " events):");
        for (int i = 0; i < laptopHistory.size(); i++) {
            DomainEvent event = laptopHistory.get(i);
            System.out.println("  [" + (i + 1) + "] " + event.getEventType() + 
                             " (v" + event.getVersion() + ") at " + event.getOccurredOn());
        }
        System.out.println();

        System.out.println("\n=== 4. Time Travel - View Product at Different Versions ===\n");
        
        System.out.println("Laptop at different points in time:");
        for (long version = 1; version <= laptop.getVersion(); version++) {
            Optional<ProductAggregate> productAtVersion = 
                repository.findByIdAtVersion(laptop.getId(), version);
            
            if (productAtVersion.isPresent()) {
                ProductAggregate p = productAtVersion.get();
                System.out.println("  Version " + version + ": " + 
                    p.getName() + " - $" + p.getPrice() + 
                    " - Qty: " + p.getQuantity());
            }
        }
        System.out.println();

        System.out.println("\n=== 5. Rebuilding State from Events ===\n");
        
        // Simulate loading from event store
        Optional<ProductAggregate> reloadedLaptop = repository.findById(laptop.getId());
        if (reloadedLaptop.isPresent()) {
            System.out.println("Laptop rebuilt from " + laptopHistory.size() + " events:");
            System.out.println(reloadedLaptop.get());
            System.out.println("State matches: " + 
                reloadedLaptop.get().getName().equals(laptop.getName()));
        }
        System.out.println();

        System.out.println("\n=== 6. Delete Product ===\n");
        
        mouse.delete("Discontinued product");
        repository.save(mouse);
        eventBus.publishAll(mouse.getUncommittedEvents());
        
        System.out.println("Mouse deleted: " + mouse.isDeleted());
        System.out.println();

        // Try to modify deleted product
        System.out.println("Trying to modify deleted product:");
        try {
            mouse.changePrice(new BigDecimal("50.00"));
        } catch (IllegalStateException e) {
            System.out.println("✓ Correctly prevented: " + e.getMessage());
        }
        System.out.println();

        System.out.println("\n=== 7. Complete Event Stream ===\n");
        
        List<DomainEvent> allEvents = eventStore.getAllEvents();
        System.out.println("Total events in store: " + allEvents.size());
        System.out.println("\nChronological event stream:");
        for (int i = 0; i < allEvents.size(); i++) {
            DomainEvent event = allEvents.get(i);
            System.out.println(String.format("  [%2d] %s - %s (v%d) - %s",
                i + 1,
                event.getOccurredOn().toString().substring(11, 19),
                event.getEventType(),
                event.getVersion(),
                event.getAggregateId().substring(0, 8) + "..."
            ));
        }
        System.out.println();

        // Print statistics
        statsHandler.printStatistics();

        System.out.println("\n=== 8. Benefits of Event Sourcing ===\n");
        System.out.println("✓ Complete audit trail - every change is recorded");
        System.out.println("✓ Time travel - can view state at any point in history");
        System.out.println("✓ Event replay - can rebuild state from events");
        System.out.println("✓ Event handlers - can create projections and react to events");
        System.out.println("✓ Debugging - can trace exactly what happened and when");
        System.out.println("✓ Event sourcing = source of truth");
        
        System.out.println("\n=== Event Sourcing Demo Complete ===");
    }
}
