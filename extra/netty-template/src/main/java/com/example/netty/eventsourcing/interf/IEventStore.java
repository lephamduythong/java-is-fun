package com.example.netty.eventsourcing.interf;

import com.example.netty.eventsourcing.event.DomainEvent;

import java.util.List;

/**
 * Event Store interface for persisting and retrieving domain events
 */
public interface IEventStore {
    
    /**
     * Append event to the store
     */
    void append(DomainEvent event);
    
    /**
     * Get all events for a specific aggregate
     */
    List<DomainEvent> getEventsForAggregate(String aggregateId);
    
    /**
     * Get all events in the store
     */
    List<DomainEvent> getAllEvents();
    
    /**
     * Get events for an aggregate up to a specific version
     */
    List<DomainEvent> getEventsForAggregateUpToVersion(String aggregateId, long version);
    
    /**
     * Get the current version of an aggregate
     */
    long getCurrentVersion(String aggregateId);
}
