package com.example.netty.eventsourcing.event;

import java.math.BigDecimal;

/**
 * Event fired when product price is changed
 */
public class ProductPriceChangedEvent extends DomainEvent {
    private final BigDecimal oldPrice;
    private final BigDecimal newPrice;

    public ProductPriceChangedEvent(String aggregateId, long version, 
                                   BigDecimal oldPrice, BigDecimal newPrice) {
        super(aggregateId, version);
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    @Override
    public String getEventType() {
        return "ProductPriceChanged";
    }

    @Override
    public String toString() {
        return "ProductPriceChangedEvent{" +
                "oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                ", " + super.toString() +
                '}';
    }
}
