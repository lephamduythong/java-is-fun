package com.example.netty.eventsourcing.aggregate;

import com.example.netty.eventsourcing.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all aggregates in Event Sourcing
 * Handles event application and uncommitted events
 */
public abstract class AggregateRoot {
    
    private String id;
    private long version;
    private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

    protected AggregateRoot() {
        this.version = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * Get all uncommitted events and clear the list
     */
    public List<DomainEvent> getUncommittedEvents() {
        List<DomainEvent> events = new ArrayList<>(uncommittedEvents);
        uncommittedEvents.clear();
        return events;
    }

    /**
     * Apply an event that comes from event store (historical)
     */
    public void loadFromHistory(DomainEvent event) {
        applyEvent(event, false);
        this.version = event.getVersion();
    }

    /**
     * Apply a new event (will be added to uncommitted events)
     */
    protected void applyChange(DomainEvent event) {
        applyEvent(event, true);
    }

    /**
     * Apply event to the aggregate
     */
    private void applyEvent(DomainEvent event, boolean isNew) {
        // Use reflection or visitor pattern to call the right apply method
        applyEventToAggregate(event);
        
        if (isNew) {
            uncommittedEvents.add(event);
        }
    }

    /**
     * Subclasses must implement this to apply events
     */
    protected abstract void applyEventToAggregate(DomainEvent event);

    /**
     * Mark all uncommitted events as committed
     */
    public void markChangesAsCommitted() {
        uncommittedEvents.clear();
    }
}
