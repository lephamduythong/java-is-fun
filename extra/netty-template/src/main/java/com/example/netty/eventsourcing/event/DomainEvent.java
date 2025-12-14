package com.example.netty.eventsourcing.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for all domain events
 */
public abstract class DomainEvent {
    private final String eventId;
    private final String aggregateId;
    private final LocalDateTime occurredOn;
    private final long version;

    protected DomainEvent(String aggregateId, long version) {
        this.eventId = UUID.randomUUID().toString();
        this.aggregateId = aggregateId;
        this.occurredOn = LocalDateTime.now();
        this.version = version;
    }

    public String getEventId() {
        return eventId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    public long getVersion() {
        return version;
    }

    public abstract String getEventType();

    @Override
    public String toString() {
        return getEventType() + "{" +
                "eventId='" + eventId + '\'' +
                ", aggregateId='" + aggregateId + '\'' +
                ", occurredOn=" + occurredOn +
                ", version=" + version +
                '}';
    }
}
