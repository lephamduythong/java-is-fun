# Event Sourcing Pattern - Hướng dẫn đầy đủ

## Tổng quan

**Event Sourcing** là pattern lưu trữ tất cả các thay đổi của ứng dụng dưới dạng sequence of events thay vì chỉ lưu state hiện tại. Events là immutable và append-only.

## Cấu trúc dự án

```
eventsourcing/
├── event/
│   ├── DomainEvent.java                    # Base event class
│   ├── ProductCreatedEvent.java            # Sự kiện tạo sản phẩm
│   ├── ProductNameChangedEvent.java        # Sự kiện đổi tên
│   ├── ProductPriceChangedEvent.java       # Sự kiện đổi giá
│   ├── ProductQuantityAdjustedEvent.java   # Sự kiện điều chỉnh số lượng
│   └── ProductDeletedEvent.java            # Sự kiện xóa sản phẩm
├── store/
│   ├── EventStore.java                     # Interface cho Event Store
│   └── InMemoryEventStore.java             # In-memory implementation
├── aggregate/
│   ├── AggregateRoot.java                  # Base aggregate class
│   └── ProductAggregate.java               # Product aggregate
├── repository/
│   └── EventSourcedProductRepository.java  # Repository sử dụng events
├── handler/
│   ├── EventHandler.java                   # Interface cho event handler
│   ├── AuditLogEventHandler.java           # Audit logging
│   ├── StatisticsEventHandler.java         # Statistics projection
│   └── EventBus.java                       # Event publisher
├── EventSourcingDemoMain.java              # Demo đầy đủ
└── InteractiveEventSourcingMain.java       # Interactive demo
```

## Khái niệm cốt lõi

### 1. Domain Events

Events đại diện cho những gì đã xảy ra trong quá khứ (past tense):
- `ProductCreatedEvent` - Sản phẩm đã được tạo
- `ProductNameChangedEvent` - Tên sản phẩm đã được thay đổi
- `ProductPriceChangedEvent` - Giá đã được thay đổi

**Đặc điểm:**
- ✅ Immutable (không thể thay đổi)
- ✅ Có timestamp (khi nào xảy ra)
- ✅ Có version (thứ tự)
- ✅ Có aggregate ID (thuộc về entity nào)

```java
public class ProductCreatedEvent extends DomainEvent {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;
    
    // Constructor and getters...
}
```

### 2. Event Store

Nơi lưu trữ tất cả events:

```java
public interface EventStore {
    void append(DomainEvent event);
    List<DomainEvent> getEventsForAggregate(String aggregateId);
    List<DomainEvent> getAllEvents();
}
```

**Đặc điểm:**
- Append-only (chỉ thêm, không sửa/xóa)
- Sequential (theo thứ tự thời gian)
- Source of truth (nguồn sự thật duy nhất)

### 3. Aggregate Root

Entity được rebuild từ events:

```java
public class ProductAggregate extends AggregateRoot {
    // Business logic (Commands)
    public static ProductAggregate create(String name, ...) {
        // Tạo event
        ProductCreatedEvent event = new ProductCreatedEvent(...);
        // Apply event
        product.applyChange(event);
        return product;
    }
    
    // Event handlers (State changes)
    private void apply(ProductCreatedEvent event) {
        this.name = event.getName();
        this.price = event.getPrice();
        // ...
    }
}
```

### 4. Event Sourced Repository

Repository rebuild state từ events:

```java
public Optional<ProductAggregate> findById(String aggregateId) {
    List<DomainEvent> events = eventStore.getEventsForAggregate(aggregateId);
    
    ProductAggregate aggregate = new ProductAggregate();
    for (DomainEvent event : events) {
        aggregate.loadFromHistory(event);  // Replay events
    }
    
    return Optional.of(aggregate);
}
```

## Luồng hoạt động

### Create Product

```
1. User creates product
        ↓
2. ProductAggregate.create()
        ↓
3. Generate ProductCreatedEvent
        ↓
4. Apply event to aggregate (change state)
        ↓
5. Save event to Event Store
        ↓
6. Publish event to Event Bus
        ↓
7. Event handlers react (audit, statistics, etc.)
```

### Update Product

```
1. Load aggregate from Event Store
        ↓
2. Replay all events to rebuild state
        ↓
3. Execute business logic (changeName, changePrice, etc.)
        ↓
4. Generate new event
        ↓
5. Apply event to aggregate
        ↓
6. Save new event to Event Store
        ↓
7. Publish event
```

## Ví dụ sử dụng

### Tạo và lưu product

```java
// Setup
EventStore eventStore = new InMemoryEventStore();
EventSourcedProductRepository repository = new EventSourcedProductRepository(eventStore);

// Create product
ProductAggregate laptop = ProductAggregate.create(
    "MacBook Pro",
    "High-end laptop",
    new BigDecimal("2499.99"),
    5
);

// Save (persists events)
repository.save(laptop);
```

### Update product

```java
// Load from event store (replays events)
Optional<ProductAggregate> productOpt = repository.findById(laptopId);
ProductAggregate laptop = productOpt.get();

// Make changes (generates new events)
laptop.changeName("MacBook Pro M3");
laptop.changePrice(new BigDecimal("2799.99"));
laptop.adjustQuantity(-2);  // Sold 2 units

// Save new events
repository.save(laptop);
```

### View event history

```java
List<DomainEvent> history = repository.getHistory(laptopId);

for (DomainEvent event : history) {
    System.out.println(event.getEventType() + " at " + event.getOccurredOn());
}

// Output:
// ProductCreated at 2025-12-14T10:00:00
// ProductNameChanged at 2025-12-14T10:05:00
// ProductPriceChanged at 2025-12-14T10:05:01
// ProductQuantityAdjusted at 2025-12-14T10:05:02
```

### Time Travel - View state tại version cụ thể

```java
// View product tại version 2
Optional<ProductAggregate> productV2 = repository.findByIdAtVersion(laptopId, 2);

// View product tại version hiện tại
Optional<ProductAggregate> currentProduct = repository.findById(laptopId);

// So sánh state tại các thời điểm khác nhau
```

## Event Handlers và Projections

### Audit Log Handler

Ghi lại tất cả events cho auditing:

```java
public class AuditLogEventHandler implements EventHandler {
    public void handle(DomainEvent event) {
        System.out.println("[AUDIT] " + event.getOccurredOn() + 
                         " | " + event.getEventType() + 
                         " | " + event.getAggregateId());
    }
}
```

### Statistics Handler (Projection)

Tạo read model từ events:

```java
public class StatisticsEventHandler implements EventHandler {
    private int totalEventsProcessed = 0;
    private int productsCreated = 0;
    private int productsDeleted = 0;
    
    public void handle(DomainEvent event) {
        totalEventsProcessed++;
        if (event instanceof ProductCreatedEvent) {
            productsCreated++;
        }
        // ...
    }
}
```

### Event Bus

Publish events đến multiple handlers:

```java
EventBus eventBus = new EventBus();
eventBus.register(new AuditLogEventHandler());
eventBus.register(new StatisticsEventHandler());

// Publish event to all handlers
eventBus.publish(event);
```

## Chạy ứng dụng

### Demo đầy đủ

```bash
mvn exec:java -Dexec.mainClass="com.example.netty.eventsourcing.EventSourcingDemoMain"
```

Demonstrates:
- ✅ Creating products
- ✅ Updating products
- ✅ Event history
- ✅ Time travel
- ✅ Rebuilding state
- ✅ Event handlers
- ✅ Statistics

### Interactive Demo

```bash
mvn exec:java -Dexec.mainClass="com.example.netty.eventsourcing.InteractiveEventSourcingMain"
```

Interactive menu cho phép:
- Create/Update/Delete products
- View current state
- View event history
- Time travel to specific versions
- View all events
- View statistics

## Lợi ích của Event Sourcing

### 1. Complete Audit Trail
Mọi thay đổi đều được ghi lại:
```
ProductCreated: "MacBook Pro" created at $2499.99
ProductPriceChanged: $2499.99 → $2799.99
ProductQuantityAdjusted: 5 → 3 (sold 2 units)
```

### 2. Time Travel
Xem state tại bất kỳ thời điểm nào:
```java
// Xem product như thế nào hôm qua
repository.findByIdAtVersion(id, yesterdayVersion);

// Xem product như thế nào sau event đầu tiên
repository.findByIdAtVersion(id, 1);
```

### 3. Event Replay
Rebuild state từ events:
```java
// Có thể rebuild lại toàn bộ database từ events
for (String aggregateId : allAggregateIds) {
    List<DomainEvent> events = eventStore.getEventsForAggregate(aggregateId);
    // Replay events to rebuild state
}
```

### 4. Debugging & Analysis
Biết chính xác điều gì đã xảy ra:
```
Bug: Giá sản phẩm sai

Investigation:
- Event 1: ProductCreated with price $100
- Event 5: ProductPriceChanged: $100 → $200  ✓
- Event 8: ProductPriceChanged: $200 → $50   ← Bug here!

Root cause: Discount logic applied incorrectly
```

### 5. Projections (Read Models)
Tạo nhiều views từ cùng events:
- Statistics projection: Đếm tổng số products
- Search projection: Full-text search index
- Report projection: Sales reports
- Notification projection: Send emails

### 6. Temporal Queries
Query dữ liệu theo thời gian:
- "Có bao nhiêu sản phẩm vào ngày 1/1/2025?"
- "Giá trung bình của sản phẩm X theo thời gian"
- "Ai đã thay đổi giá sản phẩm này?"

## So sánh: Traditional vs Event Sourcing

### Traditional (State-based)

```
Database Table: Products
ID | Name        | Price  | Quantity
1  | MacBook Pro | 2799   | 3

❌ Lost history: Không biết giá cũ là bao nhiêu
❌ Lost context: Ai đã thay đổi? Khi nào?
❌ No audit trail: Không có audit log tự động
```

### Event Sourcing

```
Event Store:
1. ProductCreated: name="MacBook Pro", price=2499, qty=5
2. ProductPriceChanged: 2499 → 2799
3. ProductQuantityAdjusted: 5 → 3 (adjustment: -2)

✓ Complete history
✓ Who, what, when cho mọi thay đổi
✓ Can rebuild state at any point in time
✓ Built-in audit trail
```

## Event Sourcing + CQRS

Event Sourcing kết hợp tốt với CQRS:

```
Commands (Write Side)
    ↓
Generate Events
    ↓
Event Store
    ↓
Event Handlers
    ↓
Update Read Models (Queries)
```

**Benefits:**
- Commands modify state thông qua events
- Queries read từ optimized read models
- Events là single source of truth
- Read models có thể rebuild từ events

## Best Practices

### 1. Event Design
```java
// ✅ Good: Specific, past tense
ProductPriceChangedEvent
ProductNameChangedEvent

// ❌ Bad: Generic, present tense
ProductUpdatedEvent
UpdateProductEvent
```

### 2. Event Immutability
```java
// ✅ Good: Immutable fields
private final String name;
private final BigDecimal price;

// ❌ Bad: Mutable fields
private String name;
public void setName(String name) { ... }
```

### 3. Event Versioning
```java
// When event structure changes, create new version
ProductCreatedEvent_V1
ProductCreatedEvent_V2

// Keep old handlers for replay
```

### 4. Snapshots (for performance)
```java
// For aggregates with many events, create snapshots
if (eventCount > 100) {
    saveSnapshot(aggregate);
}

// Load from snapshot + replay events after snapshot
```

## Khi nào dùng Event Sourcing?

### ✅ Phù hợp khi:
- Cần audit trail đầy đủ
- Cần time travel / temporal queries
- Domain logic phức tạp
- Cần debugging chi tiết
- Cần event-driven architecture
- Compliance/regulatory requirements

### ❌ Không phù hợp khi:
- Simple CRUD applications
- Không cần history
- Team chưa có kinh nghiệm
- Query requirements phức tạp (cần nhiều joins)

## Challenges

### 1. Learning Curve
Event Sourcing khác biệt so với traditional approach

### 2. Eventual Consistency
Read models có thể lag behind events

### 3. Event Schema Evolution
Cần strategy cho event versioning

### 4. Query Complexity
Queries phức tạp cần projections/read models

## Tài liệu tham khảo

- Martin Fowler: Event Sourcing
- Greg Young: CQRS and Event Sourcing
- Microsoft: CQRS Journey

---

**Event Sourcing = Events as Source of Truth** 🎯
