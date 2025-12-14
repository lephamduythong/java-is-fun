package com.example.netty.eventsourcing.event;

/**
 * Event fired when a product is deleted
 */
public class ProductDeletedEvent extends DomainEvent {
    private final String reason;

    public ProductDeletedEvent(String aggregateId, long version, String reason) {
        super(aggregateId, version);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String getEventType() {
        return "ProductDeleted";
    }

    @Override
    public String toString() {
        return "ProductDeletedEvent{" +
                "reason='" + reason + '\'' +
                ", " + super.toString() +
                '}';
    }
}
