# CQRS Pattern với Repository - Hướng dẫn

## Tổng quan

Đây là implementation hoàn chỉnh của **CQRS (Command Query Responsibility Segregation)** pattern kết hợp với **Repository Pattern** trong Java, sử dụng Netty HTTP Server.

## Cấu trúc dự án

```
cqrs/
├── domain/
│   └── Product.java              # Domain entity
├── repository/
│   ├── ProductRepository.java    # Repository interface
│   └── InMemoryProductRepository.java  # In-memory implementation
├── command/
│   ├── CreateProductCommand.java # Command để tạo sản phẩm
│   ├── UpdateProductCommand.java # Command để cập nhật sản phẩm
│   └── DeleteProductCommand.java # Command để xóa sản phẩm
├── query/
│   ├── GetProductByIdQuery.java  # Query lấy sản phẩm theo ID
│   └── GetAllProductsQuery.java  # Query lấy tất cả sản phẩm
├── handler/
│   ├── CommandHandler.java       # Interface cho Command Handler
│   ├── QueryHandler.java         # Interface cho Query Handler
│   ├── CreateProductCommandHandler.java
│   ├── UpdateProductCommandHandler.java
│   ├── DeleteProductCommandHandler.java
│   ├── GetProductByIdQueryHandler.java
│   └── GetAllProductsQueryHandler.java
├── mediator/
│   └── CQRSMediator.java         # Mediator điều phối commands/queries
├── http/
│   └── ProductCQRSHandler.java   # HTTP handler cho REST API
├── CQRSDemoMain.java             # Console demo
└── CQRSHttpServerMain.java       # HTTP Server demo
```

## Khái niệm CQRS

**CQRS** tách biệt trách nhiệm giữa:
- **Commands**: Các thao tác ghi (Create, Update, Delete) - thay đổi state
- **Queries**: Các thao tác đọc (Read) - không thay đổi state

### Lợi ích:
1. **Separation of Concerns**: Tách biệt logic đọc và ghi
2. **Scalability**: Có thể scale đọc và ghi độc lập
3. **Flexibility**: Dễ dàng tối ưu hóa từng phần
4. **Maintainability**: Code rõ ràng, dễ maintain

## CRUD Operations

### 1. CREATE - Tạo sản phẩm mới

```java
CreateProductCommand command = new CreateProductCommand(
    "Laptop Dell XPS 15",
    "High-performance laptop",
    new BigDecimal("1299.99"),
    10
);
Product product = mediator.createProduct(command);
```

**HTTP API:**
```bash
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Laptop Dell XPS 15",
  "description": "High-performance laptop",
  "price": "1299.99",
  "quantity": 10
}
```

### 2. READ - Đọc dữ liệu

**Lấy tất cả sản phẩm:**
```java
GetAllProductsQuery query = new GetAllProductsQuery();
List<Product> products = mediator.getAllProducts(query);
```

```bash
GET http://localhost:8080/api/products
```

**Lấy sản phẩm theo ID:**
```java
GetProductByIdQuery query = new GetProductByIdQuery(productId);
Optional<Product> product = mediator.getProductById(query);
```

```bash
GET http://localhost:8080/api/products/{id}
```

### 3. UPDATE - Cập nhật sản phẩm

```java
UpdateProductCommand command = new UpdateProductCommand(
    productId,
    "Laptop Dell XPS 15 (2024)",
    "Updated description",
    new BigDecimal("1499.99"),
    8
);
Product updated = mediator.updateProduct(command);
```

**HTTP API:**
```bash
PUT http://localhost:8080/api/products/{id}
Content-Type: application/json

{
  "name": "Laptop Dell XPS 15 (2024)",
  "description": "Updated description",
  "price": "1499.99",
  "quantity": 8
}
```

### 4. DELETE - Xóa sản phẩm

```java
DeleteProductCommand command = new DeleteProductCommand(productId);
Boolean deleted = mediator.deleteProduct(command);
```

**HTTP API:**
```bash
DELETE http://localhost:8080/api/products/{id}
```

## Chạy ứng dụng

### Console Demo
```bash
mvn exec:java -Dexec.mainClass="com.example.netty.cqrs.CQRSDemoMain"
```

### HTTP Server
```bash
mvn exec:java -Dexec.mainClass="com.example.netty.cqrs.CQRSHttpServerMain"
```

Server sẽ chạy tại: `http://localhost:8080`

## Test với curl

```bash
# Tạo sản phẩm
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Mouse","description":"Wireless mouse","price":"29.99","quantity":50}'

# Lấy tất cả sản phẩm
curl http://localhost:8080/api/products

# Lấy sản phẩm theo ID
curl http://localhost:8080/api/products/{id}

# Cập nhật sản phẩm
curl -X PUT http://localhost:8080/api/products/{id} \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated Mouse","price":"24.99","quantity":45}'

# Xóa sản phẩm
curl -X DELETE http://localhost:8080/api/products/{id}
```

## Validation

Hệ thống có validation cho:
- ✅ Tên sản phẩm không được rỗng
- ✅ Giá phải >= 0
- ✅ Số lượng phải >= 0
- ✅ Product ID phải tồn tại (cho update/delete)

## Mở rộng

Để thêm entity mới:

1. Tạo domain class trong `domain/`
2. Tạo repository interface và implementation
3. Tạo commands và queries
4. Tạo handlers tương ứng
5. Register handlers trong `CQRSMediator`
6. Tạo HTTP handler (nếu cần)

## Tính năng nổi bật

✨ **Repository Pattern**: Abstraction cho data access  
✨ **CQRS**: Tách biệt read và write operations  
✨ **Mediator Pattern**: Centralized request handling  
✨ **Validation**: Business rules validation  
✨ **REST API**: Full HTTP CRUD endpoints  
✨ **In-Memory Storage**: Thread-safe với ConcurrentHashMap  
✨ **Type Safety**: Strongly typed commands và queries  

## Kiến trúc

```
Client Request
     ↓
HTTP Handler
     ↓
CQRS Mediator ──→ Command Handler ──→ Repository ──→ Domain Entity
     ↓
Query Handler ──→ Repository ──→ Domain Entity
```

## Notes

- Repository sử dụng in-memory storage (có thể thay bằng database)
- Mediator pattern giúp decouple client và handlers
- Mỗi operation có handler riêng (Single Responsibility)
- Thread-safe với ConcurrentHashMap
