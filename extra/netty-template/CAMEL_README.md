# Apache Camel + Netty HTTP Sample

Đây là sample demo tích hợp Apache Camel với Netty HTTP để xây dựng REST API.

## Tổng quan

Apache Camel là một integration framework mạnh mẽ dựa trên Enterprise Integration Patterns (EIP). Sample này demo cách sử dụng Camel với Netty HTTP component để xây dựng HTTP endpoints.

## Kiến trúc

```
CamelNettyMain (Main Application)
    │
    ├─> CamelContext (Camel Runtime)
    │       │
    │       └─> CamelHttpRoute (Route Definitions)
    │               │
    │               ├─> Netty HTTP Component (HTTP Server)
    │               └─> CamelHttpProcessor (Business Logic)
    │
    └─> HTTP Endpoints (Port 9090)
            ├─ /camel/hello
            ├─ /camel/info
            ├─ /camel/echo
            ├─ /camel/transform/{name}
            ├─ /camel/delay/{seconds}
            ├─ /camel/validate
            └─ /camel/stats
```

## Components

### 1. CamelHttpRoute.java
- Định nghĩa các HTTP routes và endpoints
- Xử lý routing logic và message transformation
- Implement error handling và validation
- 7 routes khác nhau demo các pattern thường dùng

### 2. CamelHttpProcessor.java
- Custom processor cho business logic
- Process và transform messages trong route
- Enrich response với server và processing info

### 3. CamelNettyMain.java
- Main application để start Camel context
- Khởi tạo và quản lý CamelContext
- Graceful shutdown handling

## Dependencies đã thêm

```xml
<!-- Apache Camel Core -->
<dependency>
    <groupId>org.apache.camel</groupId>
    <artifactId>camel-core</artifactId>
    <version>3.21.0</version>
</dependency>

<!-- Camel Netty HTTP -->
<dependency>
    <groupId>org.apache.camel</groupId>
    <artifactId>camel-netty-http</artifactId>
    <version>3.21.0</version>
</dependency>

<!-- Camel Jackson for JSON -->
<dependency>
    <groupId>org.apache.camel</groupId>
    <artifactId>camel-jackson</artifactId>
    <version>3.21.0</version>
</dependency>
```

## Cách chạy

### Option 1: Sử dụng Maven

```bash
# Compile project
mvn clean compile

# Run Camel application
mvn exec:java -Dexec.mainClass="com.example.netty.camel.CamelNettyMain"
```

### Option 2: Sử dụng IDE

1. Open `CamelNettyMain.java`
2. Run as Java Application
3. Server sẽ start trên port 9090

### Option 3: Build và run JAR

```bash
# Build JAR
mvn clean package

# Run JAR
java -cp target/netty-server-1.0-SNAPSHOT.jar com.example.netty.camel.CamelNettyMain
```

## Available Endpoints

### 1. Simple Greeting
```bash
curl http://localhost:9090/camel/hello
```

**Response:**
```json
{
  "message": "Hello from Apache Camel!",
  "framework": "Camel + Netty HTTP",
  "timestamp": "2025-12-09T10:30:00"
}
```

### 2. Server Information
```bash
curl http://localhost:9090/camel/info
```

**Response:**
```json
{
  "status": "success",
  "message": "Request processed by Apache Camel",
  "timestamp": "2025-12-09T10:30:00",
  "request": {
    "method": "GET",
    "path": "/camel/info",
    "query": null
  },
  "server": {
    "framework": "Apache Camel",
    "component": "camel-netty-http",
    "hostname": "your-hostname",
    "camelVersion": "3.21.0"
  },
  "processing": {
    "routeId": "camel-info-route",
    "exchangeId": "...",
    "processor": "CamelHttpProcessor"
  }
}
```

### 3. Echo Service (POST)
```bash
curl -X POST http://localhost:9090/camel/echo \
  -H "Content-Type: application/json" \
  -d '{"message":"Hello from client","data":"test"}'
```

**Response:**
```json
{
  "status": "success",
  "echo": {
    "message": "Hello from client",
    "data": "test"
  },
  "receivedAt": "2025-12-09T10:30:00"
}
```

### 4. Path Parameter Transform
```bash
curl http://localhost:9090/camel/transform/Alice
```

**Response:**
```json
{
  "greeting": "Welcome, Alice!",
  "transformed": true,
  "upperCase": "ALICE",
  "length": 5
}
```

### 5. Delayed Response
```bash
curl http://localhost:9090/camel/delay/3
```

**Response** (sau 3 giây):
```json
{
  "message": "Delayed response",
  "delaySeconds": 3,
  "completedAt": "2025-12-09T10:30:03"
}
```

### 6. JSON Validation (POST)
```bash
curl -X POST http://localhost:9090/camel/validate \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","name":"John Doe"}'
```

**Response (valid):**
```json
{
  "status": "success",
  "message": "Validation passed",
  "data": {
    "email": "user@example.com",
    "name": "John Doe"
  }
}
```

**Response (invalid):**
```json
{
  "status": "error",
  "message": "Missing required fields: email, name"
}
```

### 7. Route Statistics
```bash
curl http://localhost:9090/camel/stats
```

**Response:**
```json
{
  "camelVersion": "3.21.0",
  "uptimeSeconds": 120,
  "routeCount": 7,
  "status": "running"
}
```

## Key Concepts

### 1. Route Builder
- `RouteBuilder` là base class để define routes
- Method `configure()` chứa route definitions
- DSL (Domain Specific Language) để define routing logic

### 2. Exchange
- Container cho message trong Camel
- Chứa input message, output message, headers, properties
- Flow through routes và được process bởi processors

### 3. Processor
- Interface để implement custom processing logic
- Method `process(Exchange exchange)` để transform/enrich messages
- Có thể inject vào route bằng `.process(new MyProcessor())`

### 4. Components
- `netty-http`: HTTP server/client component sử dụng Netty
- `camel-jackson`: JSON marshalling/unmarshalling
- `camel-bean`: Bean integration và method invocation

### 5. Enterprise Integration Patterns (EIP)
Demo trong routes:
- **Content-Based Router**: Route based on message content (choice/when/otherwise)
- **Message Filter**: Filter messages based on conditions
- **Message Translator**: Transform message format
- **Dead Letter Channel**: Error handling (onException)
- **Wire Tap**: Log messages without affecting flow

## Features Demo

### Error Handling
```java
onException(Exception.class)
    .handled(true)
    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
    .setBody().constant("{...error response...}");
```

### Content-Based Routing
```java
.choice()
    .when(header(Exchange.HTTP_METHOD).isEqualTo("POST"))
        .process(...)
    .otherwise()
        .setBody().constant("{...error...}");
```

### Message Transformation
```java
.unmarshal().json()  // Parse JSON
.process(exchange -> {
    // Custom processing
})
.marshal().json()    // Convert to JSON
```

### Dynamic Routing
```java
from("netty-http:http://0.0.0.0:9090/camel/transform/*?matchOnUriPrefix=true")
    .process(exchange -> {
        String path = exchange.getIn().getHeader("CamelHttpPath", String.class);
        // Extract and process path parameter
    });
```

## So sánh với Pure Netty

### Pure Netty (HttpServerHandler)
- Low-level HTTP handling
- Manual request/response processing
- Code nhiều hơn cho routing logic
- Flexible nhưng verbose

### Camel + Netty HTTP
- High-level routing DSL
- Built-in message transformation
- Enterprise Integration Patterns
- Less code, more declarative
- Easy to add new routes
- Built-in error handling

## Testing

### Sử dụng curl
Xem các ví dụ curl ở trên

### Sử dụng Postman
1. Import các endpoints vào Postman
2. Set Content-Type: application/json cho POST requests
3. Test các endpoints với different payloads

### Sử dụng Browser
- Truy cập trực tiếp các GET endpoints:
  - http://localhost:9090/camel/hello
  - http://localhost:9090/camel/info
  - http://localhost:9090/camel/stats

## Logs

Khi chạy application, bạn sẽ thấy logs:
```
[main] INFO org.apache.camel.impl.engine.AbstractCamelContext - Apache Camel 3.21.0 starting
[main] INFO camel-hello-route - Received request: GET /camel/hello
[main] INFO camel-hello-route - Response sent: {...}
```

## Advanced Usage

### Thêm route mới

1. Edit `CamelHttpRoute.java`
2. Thêm route definition trong method `configure()`:

```java
from("netty-http:http://0.0.0.0:9090/camel/myroute?matchOnUriPrefix=true")
    .routeId("my-custom-route")
    .log("Processing custom route")
    .process(exchange -> {
        // Your logic here
    });
```

### Sử dụng với ActiveMQ

Có thể combine với ActiveMQ để tạo messaging integration:

```java
from("netty-http:http://0.0.0.0:9090/camel/publish?matchOnUriPrefix=true")
    .to("activemq:queue:myQueue");

from("activemq:queue:myQueue")
    .log("Received from queue: ${body}");
```

## Tài liệu tham khảo

- [Apache Camel Documentation](https://camel.apache.org/manual/)
- [Camel Netty HTTP Component](https://camel.apache.org/components/latest/netty-http-component.html)
- [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/)
- [Camel in Action Book](https://www.manning.com/books/camel-in-action-second-edition)

## Troubleshooting

### Port already in use
```
Address already in use: bind
```
**Solution:** Change port in `CamelHttpRoute.java` hoặc stop process đang dùng port 9090

### Dependency conflicts
```
mvn dependency:tree
```
Check for conflicts và exclude nếu cần

### JSON parsing errors
Ensure Content-Type header: `application/json`

## Next Steps

- Thêm authentication/authorization
- Integrate với database
- Add more EIP patterns
- Implement async processing
- Add metrics và monitoring
- Integrate với message brokers (ActiveMQ, Kafka)
- Add REST API documentation (OpenAPI/Swagger)
