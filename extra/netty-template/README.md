# Netty HTTP Server

Project Maven đơn giản sử dụng Netty embedded server với 2 endpoints GET và POST.

## Cấu trúc project

```
netty-template/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── netty/
                        ├── NettyServerApplication.java
                        ├── HttpServerInitializer.java
                        └── HttpServerHandler.java
```

## Yêu cầu

- Java 11 trở lên
- Maven 3.6+

## Cài đặt và chạy

1. Build project:
```bash
mvn clean package
```

2. Chạy server:
```bash
mvn exec:java -Dexec.mainClass="com.example.netty.NettyServerApplication"
```

Hoặc chạy trực tiếp từ IDE bằng cách run class `NettyServerApplication`.

Server sẽ khởi động trên port **8080**.

## Endpoints

### 1. GET /hello

**Request:**
```bash
curl http://localhost:8080/hello
```

**Response:**
```json
{
  "message": "Hello from Netty Server!",
  "method": "GET",
  "timestamp": 1701950400000,
  "status": "success"
}
```

### 2. POST /data

**Request:**
```bash
curl -X POST http://localhost:8080/data \
  -H "Content-Type: application/json" \
  -d '{"name": "John", "age": 30}'
```

**Response:**
```json
{
  "message": "Data received successfully",
  "method": "POST",
  "receivedData": "{\"name\": \"John\", \"age\": 30}",
  "parsedData": {
    "name": "John",
    "age": 30
  },
  "timestamp": 1701950400000,
  "status": "success"
}
```

## Dependencies

- **Netty 4.1.104.Final**: Framework bất đồng bộ cho network applications
- **Gson 2.10.1**: Xử lý JSON

## Tính năng

- ✅ Embedded Netty HTTP server
- ✅ GET endpoint trả về JSON
- ✅ POST endpoint nhận và parse JSON
- ✅ Error handling
- ✅ Async non-blocking I/O
