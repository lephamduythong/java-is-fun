package com.example.netty.eventsourcing.repository;

import com.example.netty.eventsourcing.aggregate.ProductAggregate;
import com.example.netty.eventsourcing.event.DomainEvent;
import com.example.netty.eventsourcing.interf.IEventStore;

import java.util.List;
import java.util.Optional;

/**
 * Event Sourced Repository for Product Aggregate
 * Rebuilds aggregate state from events
 */
public class EventSourcedProductRepository {
    
    private final IEventStore eventStore;

    public EventSourcedProductRepository(IEventStore eventStore) {
        this.eventStore = eventStore;
    }

    /**
     * Save aggregate by persisting its uncommitted events
     */
    public void save(ProductAggregate aggregate) {
        List<DomainEvent> uncommittedEvents = aggregate.getUncommittedEvents();
        
        for (DomainEvent event : uncommittedEvents) {
            eventStore.append(event);
        }
        
        aggregate.markChangesAsCommitted();
        
        // Update version
        aggregate.setVersion(eventStore.getCurrentVersion(aggregate.getId()));
    }

    /**
     * Load aggregate by replaying all its events
     */
    public Optional<ProductAggregate> findById(String aggregateId) {
        List<DomainEvent> events = eventStore.getEventsForAggregate(aggregateId);
        
        if (events.isEmpty()) {
            return Optional.empty();
        }
        
        ProductAggregate aggregate = new ProductAggregate();
        aggregate.setId(aggregateId);
        
        // Replay all events to rebuild state
        for (DomainEvent event : events) {
            aggregate.loadFromHistory(event);
        }
        
        return Optional.of(aggregate);
    }

    /**
     * Load aggregate at a specific version (time travel)
     */
    public Optional<ProductAggregate> findByIdAtVersion(String aggregateId, long version) {
        List<DomainEvent> events = eventStore.getEventsForAggregateUpToVersion(aggregateId, version);
        
        if (events.isEmpty()) {
            return Optional.empty();
        }
        
        ProductAggregate aggregate = new ProductAggregate();
        aggregate.setId(aggregateId);
        
        // Replay events up to the specified version
        for (DomainEvent event : events) {
            aggregate.loadFromHistory(event);
        }
        
        return Optional.of(aggregate);
    }

    /**
     * Check if aggregate exists
     */
    public boolean exists(String aggregateId) {
        return !eventStore.getEventsForAggregate(aggregateId).isEmpty();
    }

    /**
     * Get event history for an aggregate
     */
    public List<DomainEvent> getHistory(String aggregateId) {
        return eventStore.getEventsForAggregate(aggregateId);
    }
}
