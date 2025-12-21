package com.example.netty.eventsourcing.store;

import com.example.netty.eventsourcing.event.DomainEvent;
import com.example.netty.eventsourcing.interf.IEventStore;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * In-memory implementation of Event Store
 * Thread-safe implementation for concurrent access
 */
public class InMemoryEventStore implements IEventStore {
    
    // Store all events in order
    private final List<DomainEvent> events = new CopyOnWriteArrayList<>();
    
    // Index: aggregateId -> list of events
    private final Map<String, List<DomainEvent>> eventsByAggregate = new ConcurrentHashMap<>();

    @Override
    public void append(DomainEvent event) {
        // Add to main store
        events.add(event);
        
        // Add to index
        eventsByAggregate
            .computeIfAbsent(event.getAggregateId(), k -> new CopyOnWriteArrayList<>())
            .add(event);
        
        System.out.println("[EventStore] Event appended: " + event.getEventType() + 
                         " for aggregate " + event.getAggregateId() + 
                         " (version " + event.getVersion() + ")");
    }

    @Override
    public List<DomainEvent> getEventsForAggregate(String aggregateId) {
        return new ArrayList<>(
            eventsByAggregate.getOrDefault(aggregateId, Collections.emptyList())
        );
    }

    @Override
    public List<DomainEvent> getAllEvents() {
        return new ArrayList<>(events);
    }

    @Override
    public List<DomainEvent> getEventsForAggregateUpToVersion(String aggregateId, long version) {
        return eventsByAggregate
            .getOrDefault(aggregateId, Collections.emptyList())
            .stream()
            .filter(e -> e.getVersion() <= version)
            .collect(Collectors.toList());
    }

    @Override
    public long getCurrentVersion(String aggregateId) {
        List<DomainEvent> aggregateEvents = eventsByAggregate.get(aggregateId);
        if (aggregateEvents == null || aggregateEvents.isEmpty()) {
            return 0;
        }
        return aggregateEvents.get(aggregateEvents.size() - 1).getVersion();
    }
    
    /**
     * Get event count for statistics
     */
    public int getEventCount() {
        return events.size();
    }
    
    /**
     * Get aggregate count for statistics
     */
    public int getAggregateCount() {
        return eventsByAggregate.size();
    }
    
    /**
     * Clear all events (for testing)
     */
    public void clear() {
        events.clear();
        eventsByAggregate.clear();
    }
}
