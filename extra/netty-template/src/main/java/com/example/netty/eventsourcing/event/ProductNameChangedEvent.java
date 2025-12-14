package com.example.netty.eventsourcing.event;

/**
 * Event fired when product name is changed
 */
public class ProductNameChangedEvent extends DomainEvent {
    private final String oldName;
    private final String newName;

    public ProductNameChangedEvent(String aggregateId, long version, 
                                  String oldName, String newName) {
        super(aggregateId, version);
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }

    @Override
    public String getEventType() {
        return "ProductNameChanged";
    }

    @Override
    public String toString() {
        return "ProductNameChangedEvent{" +
                "oldName='" + oldName + '\'' +
                ", newName='" + newName + '\'' +
                ", " + super.toString() +
                '}';
    }
}
