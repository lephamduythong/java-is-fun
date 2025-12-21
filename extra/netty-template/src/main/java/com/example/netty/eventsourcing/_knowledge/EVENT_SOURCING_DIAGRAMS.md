# Event Sourcing - Sơ đồ minh họa

## 1. Kiến trúc tổng quan

```
┌─────────────────────────────────────────────────────────────────┐
│                    Event Sourcing Architecture                  │
└─────────────────────────────────────────────────────────────────┘

┌──────────────┐
│   Client     │
│  (Demo App)  │
└──────┬───────┘
       │
       │ 1. Create/Update Commands
       ▼
┌─────────────────────┐
│  ProductAggregate   │◄──────────────────┐
│  - Business Logic   │                   │
│  - Validation       │                   │ 6. Replay Events
└──────┬──────────────┘                   │    (Rebuild State)
       │                                  │
       │ 2. Generate Events               │
       ▼                                  │
┌─────────────────────┐           ┌───────┴─────────┐
│   Domain Events     │           │  Event Sourced  │
│ - ProductCreated    │           │   Repository    │
│ - NameChanged       │           └──────┬──────────┘
│ - PriceChanged      │                  │
│ - QuantityAdjusted  │                  │ 5. Load Events
│ - ProductDeleted    │                  │
└──────┬──────────────┘                  │
       │                                 │
       │ 3. Append Events                │
       ▼                                 │
┌─────────────────────┐                  │
│   Event Store       │                  │
│  (Append-Only)      │──────────────────┘
│                     │
│ Event 1: Created    │
│ Event 2: NameChanged│
│ Event 3: PriceChg   │
│ Event 4: QtyAdjust  │
│ ...                 │
└──────┬──────────────┘
       │
       │ 4. Publish Events
       ▼
┌─────────────────────┐
│    Event Bus        │
│   (Mediator)        │
└──────┬──────────────┘
       │
       ├────────────┬─────────────┐
       ▼            ▼             ▼
┌──────────┐  ┌──────────┐  ┌──────────┐
│  Audit   │  │Statistics│  │  Email   │
│  Logger  │  │  Handler │  │  Sender  │
└──────────┘  └──────────┘  └──────────┘
```

## 2. Luồng Create Product

```
Step 1: Create Product Command
═══════════════════════════════

┌─────────────────────────────────────────────────────────────────┐
│  ProductAggregate.create("MacBook Pro", ..., $2499.99, 5)      │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
                ┌────────────────────────┐
                │  Business Validation   │
                │  - Name not empty?     │
                │  - Price >= 0?         │
                │  - Quantity >= 0?      │
                └────────┬───────────────┘
                         │ ✓ Valid
                         ▼
            ┌────────────────────────────┐
            │  Generate Event            │
            │                            │
            │  ProductCreatedEvent       │
            │  {                         │
            │    id: "uuid-123...",      │
            │    version: 1,             │
            │    name: "MacBook Pro",    │
            │    price: 2499.99,         │
            │    quantity: 5,            │
            │    occurredOn: "now"       │
            │  }                         │
            └────────┬───────────────────┘
                     │
                     ▼
        ┌────────────────────────────────┐
        │  Apply Event to Aggregate      │
        │  (Change Internal State)       │
        │                                │
        │  this.name = "MacBook Pro"     │
        │  this.price = 2499.99          │
        │  this.quantity = 5             │
        │  this.version = 1              │
        └────────┬───────────────────────┘
                 │
                 ▼
    ┌────────────────────────────────────┐
    │  Add to Uncommitted Events         │
    │  uncommittedEvents.add(event)      │
    └────────┬───────────────────────────┘
             │
             ▼
┌────────────────────────────────────────┐
│  repository.save(aggregate)            │
│                                        │
│  → eventStore.append(event)            │
│  → mark events as committed            │
└────────┬───────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│  eventBus.publishAll(events)            │
│                                         │
│  → AuditLogHandler logs event           │
│  → StatisticsHandler counts++           │
└─────────────────────────────────────────┘

Result: Product created and stored as Event #1 ✓
```

## 3. Luồng Update Product (Multiple Events)

```
Step 2: Update Product - Generate Multiple Events
═════════════════════════════════════════════════

TIME: 10:00:00                    EVENT STORE STATE
─────────────────────────────────────────────────────────────
                                  [Event 1] ProductCreated
                                    - name: "MacBook Pro"
                                    - price: $2499.99
                                    - qty: 5

laptop.changeName("MacBook Pro M3")
         │
         ▼
    [Generate Event]
    ProductNameChangedEvent
    - version: 2
    - oldName: "MacBook Pro"
    - newName: "MacBook Pro M3"
         │
         ▼                         [Event 1] ProductCreated
    [Apply Event]                  [Event 2] NameChanged ← NEW
    name = "MacBook Pro M3"          - "MacBook Pro" → "MacBook Pro M3"
    version = 2


laptop.changePrice(new BigDecimal("2799.99"))
         │
         ▼
    [Generate Event]
    ProductPriceChangedEvent
    - version: 3
    - oldPrice: $2499.99
    - newPrice: $2799.99
         │
         ▼                         [Event 1] ProductCreated
    [Apply Event]                  [Event 2] NameChanged
    price = $2799.99               [Event 3] PriceChanged ← NEW
    version = 3                      - $2499.99 → $2799.99


laptop.adjustQuantity(-2)  // Sold 2 units
         │
         ▼
    [Generate Event]
    ProductQuantityAdjustedEvent
    - version: 4
    - oldQty: 5
    - newQty: 3
    - adjustment: -2
         │
         ▼                         [Event 1] ProductCreated
    [Apply Event]                  [Event 2] NameChanged
    quantity = 3                   [Event 3] PriceChanged
    version = 4                    [Event 4] QuantityAdjusted ← NEW
                                     - 5 → 3 (sold 2)

─────────────────────────────────────────────────────────────
FINAL STATE:
Product {
  name: "MacBook Pro M3",
  price: $2799.99,
  quantity: 3,
  version: 4
}

COMPLETE HISTORY: 4 events stored ✓
```

## 4. Time Travel - View State at Different Versions

```
Time Travel: Rebuild Product at Specific Version
═════════════════════════════════════════════════

┌─────────────────────────────────────────────────────────────┐
│  Event Store (Complete History)                             │
├─────────────────────────────────────────────────────────────┤
│  [1] ProductCreated      → v1                               │
│  [2] NameChanged         → v2                               │
│  [3] PriceChanged        → v3                               │
│  [4] QuantityAdjusted    → v4                               │
└─────────────────────────────────────────────────────────────┘

┌─────────────────┐
│ Version 1       │  Replay: Event 1 only
│ (Initial)       │  ═════════════════════
└────────┬────────┘
         │           ProductCreated Event
         │           ─────────────────────
         │           name: "MacBook Pro"
         ▼           price: $2499.99
    ╔══════════╗    quantity: 5
    ║ MacBook  ║
    ║   Pro    ║
    ║ $2499.99 ║
    ║  Qty: 5  ║
    ╚══════════╝

┌─────────────────┐
│ Version 2       │  Replay: Events 1-2
│ (After rename)  │  ═══════════════════
└────────┬────────┘
         │           1. ProductCreated
         │           2. NameChanged
         ▼              → name becomes "MacBook Pro M3"
    ╔══════════╗
    ║ MacBook  ║
    ║  Pro M3  ║    ← Name changed!
    ║ $2499.99 ║
    ║  Qty: 5  ║
    ╚══════════╝

┌─────────────────┐
│ Version 3       │  Replay: Events 1-3
│ (After price)   │  ═══════════════════
└────────┬────────┘
         │           1. ProductCreated
         │           2. NameChanged
         ▼           3. PriceChanged
    ╔══════════╗       → price becomes $2799.99
    ║ MacBook  ║
    ║  Pro M3  ║
    ║ $2799.99 ║    ← Price changed!
    ║  Qty: 5  ║
    ╚══════════╝

┌─────────────────┐
│ Version 4       │  Replay: Events 1-4
│ (Current)       │  ═══════════════════
└────────┬────────┘
         │           1. ProductCreated
         │           2. NameChanged
         ▼           3. PriceChanged
    ╔══════════╗    4. QuantityAdjusted
    ║ MacBook  ║       → quantity becomes 3
    ║  Pro M3  ║
    ║ $2799.99 ║
    ║  Qty: 3  ║    ← Quantity changed!
    ╚══════════╝

═════════════════════════════════════════════════════════════
Key Insight: Each version is recreated by replaying events!
═════════════════════════════════════════════════════════════
```

## 5. Event Replay Process (Rebuild from Events)

```
Rebuilding Aggregate State from Event Stream
═════════════════════════════════════════════

┌──────────────────────────────────────────────────────────┐
│  repository.findById("laptop-123")                       │
└────────────────────────┬─────────────────────────────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │  Load Events from    │
              │  Event Store         │
              └──────────┬───────────┘
                         │
                         ▼
        ┌────────────────────────────────┐
        │  Events for laptop-123:        │
        │  [1] ProductCreated            │
        │  [2] NameChanged               │
        │  [3] PriceChanged              │
        │  [4] QuantityAdjusted          │
        └────────┬───────────────────────┘
                 │
                 ▼
    ┌────────────────────────────────────┐
    │  Create Empty Aggregate            │
    │  aggregate = new ProductAggregate()│
    └────────┬───────────────────────────┘
             │
             │  Replay Each Event in Order
             │
             ├──► Event 1: ProductCreated
             │    │
             │    └─► apply(ProductCreatedEvent)
             │         ├─ name = "MacBook Pro"
             │         ├─ price = $2499.99
             │         ├─ quantity = 5
             │         └─ version = 1
             │
             │         State: {MacBook Pro, $2499.99, qty:5, v:1}
             │
             ├──► Event 2: NameChanged
             │    │
             │    └─► apply(ProductNameChangedEvent)
             │         ├─ name = "MacBook Pro M3"
             │         └─ version = 2
             │
             │         State: {MacBook Pro M3, $2499.99, qty:5, v:2}
             │
             ├──► Event 3: PriceChanged
             │    │
             │    └─► apply(ProductPriceChangedEvent)
             │         ├─ price = $2799.99
             │         └─ version = 3
             │
             │         State: {MacBook Pro M3, $2799.99, qty:5, v:3}
             │
             └──► Event 4: QuantityAdjusted
                  │
                  └─► apply(ProductQuantityAdjustedEvent)
                       ├─ quantity = 3
                       └─ version = 4

                       Final State: {MacBook Pro M3, $2799.99, qty:3, v:4}

┌──────────────────────────────────────────────────────────┐
│  Return Fully Reconstructed Aggregate                    │
│  ✓ State rebuilt from 4 events                          │
│  ✓ Version = 4 (matches latest event)                   │
│  ✓ Complete audit trail preserved                       │
└──────────────────────────────────────────────────────────┘
```

## 6. Event Handlers và Projections

```
Event Bus Publishing to Multiple Handlers
══════════════════════════════════════════

                    ┌─────────────────┐
                    │   Event Bus     │
                    │   (Mediator)    │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
              ▼              ▼              ▼
    ┌─────────────────┐ ┌────────────┐ ┌─────────────┐
    │  Audit Logger   │ │ Statistics │ │   Email     │
    │                 │ │  Handler   │ │  Notifier   │
    └─────────────────┘ └────────────┘ └─────────────┘


Handler 1: Audit Log
════════════════════
Event: ProductCreated
  ↓
[AUDIT] 2025-12-14 10:00:00 | ProductCreated | laptop-123 | v1
        → Product created: MacBook Pro at $2499.99

Event: PriceChanged
  ↓
[AUDIT] 2025-12-14 10:05:00 | ProductPriceChanged | laptop-123 | v3
        → Price changed: $2499.99 → $2799.99


Handler 2: Statistics (Projection)
═══════════════════════════════════
Event: ProductCreated
  ↓
productsCreated++      → 1
totalEvents++          → 1

Event: ProductCreated (mouse)
  ↓
productsCreated++      → 2
totalEvents++          → 2

Event: ProductDeleted
  ↓
productsDeleted++      → 1
totalEvents++          → 8

Final Stats:
─────────────
Total events: 8
Products created: 2
Products deleted: 1
Active products: 1


Handler 3: Read Model Projection
═════════════════════════════════
Event Stream → Build Optimized Query Model

ProductCreated (laptop-123)
  ↓
ProductView Table:
  id: laptop-123
  name: "MacBook Pro"
  price: 2499.99
  searchable_name: "macbook pro"  ← for full-text search

NameChanged
  ↓
UPDATE ProductView
  name = "MacBook Pro M3"
  searchable_name = "macbook pro m3"

Result: Separate optimized read model for queries!
```

## 7. Complete Demo Flow

```
Event Sourcing Demo - Complete Flow
════════════════════════════════════

┌────────────────────────────────────────────────────────────────┐
│  PHASE 1: Creating Products                                    │
└────────────────────────────────────────────────────────────────┘

Create Laptop                          Create Mouse
     │                                       │
     ▼                                       ▼
ProductCreatedEvent (v1)              ProductCreatedEvent (v1)
     │                                       │
     ├─► Event Store                        ├─► Event Store
     └─► Event Bus                          └─► Event Bus
         │                                       │
         ├─► Audit: Logged                      ├─► Audit: Logged
         └─► Stats: created++                   └─► Stats: created++

Event Store:
[1] ProductCreated (laptop-123, v1)
[2] ProductCreated (mouse-456, v1)

┌────────────────────────────────────────────────────────────────┐
│  PHASE 2: Modifying Products                                   │
└────────────────────────────────────────────────────────────────┘

Laptop Updates                         Mouse Updates
     │                                       │
     ├─► changeName()                        ├─► changePrice()
     │   → NameChanged (v2)                  │   → PriceChanged (v2)
     │                                       │
     ├─► changePrice()                       └─► adjustQuantity(+10)
     │   → PriceChanged (v3)                     → QuantityAdjusted (v3)
     │
     └─► adjustQuantity(-2)
         → QuantityAdjusted (v4)

Event Store:
[1] ProductCreated (laptop-123, v1)
[2] ProductCreated (mouse-456, v1)
[3] NameChanged (laptop-123, v2)      ← Laptop events
[4] PriceChanged (laptop-123, v3)     ←
[5] QuantityAdjusted (laptop-123, v4) ←
[6] PriceChanged (mouse-456, v2)      ← Mouse events
[7] QuantityAdjusted (mouse-456, v3)  ←

┌────────────────────────────────────────────────────────────────┐
│  PHASE 3: Time Travel                                          │
└────────────────────────────────────────────────────────────────┘

Query: "Show me laptop at version 2"
  ↓
Load events 1-2 for laptop-123
  ↓
Replay:
  Event 1: Created → name="MacBook Pro", price=$2499.99, qty=5
  Event 2: NameChanged → name="MacBook Pro M3"
  ↓
Result: Version 2 state = {MacBook Pro M3, $2499.99, qty:5}

┌────────────────────────────────────────────────────────────────┐
│  PHASE 4: Delete Product                                       │
└────────────────────────────────────────────────────────────────┘

mouse.delete("Discontinued")
     │
     ▼
ProductDeletedEvent (v4)
     │
     ├─► Event Store
     └─► Event Bus
         └─► Stats: deleted++

Event Store:
[1] ProductCreated (laptop-123, v1)
[2] ProductCreated (mouse-456, v1)
[3] NameChanged (laptop-123, v2)
[4] PriceChanged (laptop-123, v3)
[5] QuantityAdjusted (laptop-123, v4)
[6] PriceChanged (mouse-456, v2)
[7] QuantityAdjusted (mouse-456, v3)
[8] ProductDeleted (mouse-456, v4)     ← New event

Try to update deleted product:
  mouse.changePrice($50)
  ↓
  ✗ IllegalStateException: "Cannot modify deleted product"

┌────────────────────────────────────────────────────────────────┐
│  FINAL STATE                                                   │
└────────────────────────────────────────────────────────────────┘

Event Store: 8 events total
  Laptop (laptop-123): 4 events → Active, version 4
  Mouse (mouse-456): 4 events → Deleted, version 4

Statistics:
  Total events: 8
  Products created: 2
  Products deleted: 1
  Active products: 1

Audit Trail: Complete history of all changes ✓
Time Travel: Can view any version ✓
Event Replay: Can rebuild state anytime ✓
```

## 8. Traditional vs Event Sourcing Comparison

```
Traditional Approach (State-Based)
═══════════════════════════════════

Database Table: products
┌──────┬─────────────────┬──────────┬──────────┬─────────────┐
│ ID   │ Name            │ Price    │ Quantity │ Updated     │
├──────┼─────────────────┼──────────┼──────────┼─────────────┤
│ 123  │ MacBook Pro M3  │ 2799.99  │ 3        │ 10:05:00    │
└──────┴─────────────────┴──────────┴──────────┴─────────────┘

Problems:
❌ Lost history: What was the old name?
❌ Lost context: Who changed the price? Why?
❌ No audit trail: Can't prove what happened
❌ Can't debug: Can't replay changes
❌ Can't time travel: Can't see yesterday's state


Event Sourcing Approach (Event-Based)
═════════════════════════════════════

Event Store: product_events
┌────┬──────────────────┬─────┬─────────────────────────┬──────────┐
│ #  │ Event Type       │ Ver │ Details                 │ Time     │
├────┼──────────────────┼─────┼─────────────────────────┼──────────┤
│ 1  │ ProductCreated   │ 1   │ MacBook Pro, $2499, 5   │ 10:00:00 │
│ 2  │ NameChanged      │ 2   │ "Pro" → "Pro M3"        │ 10:02:00 │
│ 3  │ PriceChanged     │ 3   │ $2499 → $2799           │ 10:03:00 │
│ 4  │ QuantityAdjusted │ 4   │ 5 → 3 (sold 2)          │ 10:05:00 │
└────┴──────────────────┴─────┴─────────────────────────┴──────────┘

Current State: Reconstructed from events
{
  name: "MacBook Pro M3",
  price: $2799.99,
  quantity: 3,
  version: 4
}

Benefits:
✓ Complete history: All changes recorded
✓ Full context: Who, what, when, why for every change
✓ Built-in audit trail: Events = audit log
✓ Time travel: Can view state at any point
✓ Debugging: Can replay and analyze
✓ Projections: Can create multiple read models
```

## 9. Key Takeaways

```
┌────────────────────────────────────────────────────────────────┐
│  Event Sourcing = Events as Source of Truth                   │
└────────────────────────────────────────────────────────────────┘

Traditional Database          Event Sourcing
─────────────────────────────────────────────────────────────────
Store: Current State          Store: Event History
Update: Overwrite row         Update: Append new event
Delete: Remove data           Delete: Add "Deleted" event
History: Lost                 History: Preserved forever
Audit: Separate system        Audit: Built-in
Debug: Hard to trace          Debug: Replay events
Query: Fast                   Query: Build from events

Use Event Sourcing When:
✓ Need complete audit trail
✓ Need to debug complex workflows
✓ Need time travel / temporal queries
✓ Need event-driven architecture
✓ Compliance requirements
✓ Complex domain logic

Avoid Event Sourcing When:
✗ Simple CRUD application
✗ No need for history
✗ Team lacks experience
✗ Performance critical simple queries
