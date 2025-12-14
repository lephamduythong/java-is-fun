package com.example.netty.eventsourcing.event;

/**
 * Event fired when product quantity is adjusted
 */
public class ProductQuantityAdjustedEvent extends DomainEvent {
    private final Integer oldQuantity;
    private final Integer newQuantity;
    private final Integer adjustment;

    public ProductQuantityAdjustedEvent(String aggregateId, long version, 
                                       Integer oldQuantity, Integer newQuantity, Integer adjustment) {
        super(aggregateId, version);
        this.oldQuantity = oldQuantity;
        this.newQuantity = newQuantity;
        this.adjustment = adjustment;
    }

    public Integer getOldQuantity() {
        return oldQuantity;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    @Override
    public String getEventType() {
        return "ProductQuantityAdjusted";
    }

    @Override
    public String toString() {
        return "ProductQuantityAdjustedEvent{" +
                "oldQuantity=" + oldQuantity +
                ", newQuantity=" + newQuantity +
                ", adjustment=" + adjustment +
                ", " + super.toString() +
                '}';
    }
}
