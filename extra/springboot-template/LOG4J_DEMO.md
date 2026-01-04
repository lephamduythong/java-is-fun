# Log4j2 Demo - Hướng dẫn sử dụng

## Cấu hình Log4j2

Project này đã được cấu hình để sử dụng Log4j2 để ghi log ra file.

### Cấu trúc file log

Tất cả log files được lưu trong thư mục `logs/`:
- **logs/application.log** - Chứa tất cả các log từ INFO trở lên
- **logs/error.log** - Chỉ chứa các log ERROR và FATAL
- Log files tự động rotate theo ngày và kích thước (10MB)

### Các log levels (từ thấp đến cao)

1. **TRACE** - Thông tin chi tiết nhất (thường dùng khi debug sâu)
2. **DEBUG** - Thông tin debug chi tiết
3. **INFO** - Thông tin chung về hoạt động của ứng dụng
4. **WARN** - Cảnh báo về tình huống có thể gây vấn đề
5. **ERROR** - Lỗi xảy ra nhưng ứng dụng vẫn có thể tiếp tục
6. **FATAL** - Lỗi nghiêm trọng có thể khiến ứng dụng dừng

## Demo Endpoints

### 1. Hello Endpoint với logging cơ bản
```
GET http://localhost:8080/
GET http://localhost:8080/log-demo?name=John
GET http://localhost:8080/log-demo?name=error
```

### 2. Test tất cả các log levels
```
GET http://localhost:8080/api/logging/test-all-levels
```
Endpoint này sẽ ghi log với tất cả các levels để bạn có thể xem trong file log.

### 3. Log user action (POST request)
```
POST http://localhost:8080/api/logging/user-action
Content-Type: application/json

{
  "userId": "user123",
  "action": "login"
}
```

### 4. Simulate error để test error logging
```
GET http://localhost:8080/api/logging/simulate-error
```

### 5. Performance logging
```
GET http://localhost:8080/api/logging/performance-log
```

## Cách sử dụng Log4j2 trong code

### Import Logger
```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
```

### Khởi tạo Logger trong class
```java
private static final Logger logger = LogManager.getLogger(YourClassName.class);
```

### Ghi log với các levels
```java
logger.trace("Detailed trace information");
logger.debug("Debug information: {}", variable);
logger.info("Informational message: {}", value);
logger.warn("Warning message");
logger.error("Error occurred: {}", errorMessage, exception);
logger.fatal("Fatal error");
```

### Ghi log với parameters (khuyến khích sử dụng)
```java
// Tốt - sử dụng parameterized logging
logger.info("User {} logged in at {}", username, timestamp);

// Tránh - string concatenation
logger.info("User " + username + " logged in at " + timestamp);
```

### Ghi log exception
```java
try {
    // some code
} catch (Exception e) {
    logger.error("Error processing request: {}", e.getMessage(), e);
}
```

## Build và chạy project

### Build project
```bash
mvn clean package
```

### Chạy application
```bash
mvn spring-boot:run
```

Hoặc deploy file WAR `target/test-api.war` lên WildFly/JBoss.

## Kiểm tra logs

1. Sau khi chạy application và gọi các endpoints
2. Kiểm tra thư mục `logs/`
3. Xem file `logs/application.log` để thấy tất cả các log
4. Xem file `logs/error.log` để chỉ xem các error logs

## Cấu hình Log4j2

File cấu hình: `src/main/resources/log4j2.xml`

### Thay đổi log level
Để thay đổi log level cho package của bạn, sửa trong `log4j2.xml`:

```xml
<Logger name="com.example.springboottemplate" level="DEBUG" additivity="false">
    <AppenderRef ref="Console"/>
    <AppenderRef ref="FileAppender"/>
    <AppenderRef ref="ErrorFileAppender"/>
</Logger>
```

Thay `level="DEBUG"` thành `INFO`, `WARN`, hoặc `ERROR` tùy nhu cầu.

### Thay đổi vị trí lưu log files
Sửa property trong `log4j2.xml`:

```xml
<Property name="APP_LOG_ROOT">logs</Property>
```

### Thay đổi log pattern
Sửa property trong `log4j2.xml`:

```xml
<Property name="LOG_PATTERN">%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n</Property>
```

## Best Practices

1. **Sử dụng đúng log level**: 
   - DEBUG cho thông tin kỹ thuật khi phát triển
   - INFO cho business events quan trọng
   - WARN cho các vấn đề có thể xảy ra
   - ERROR cho các lỗi cần được chú ý

2. **Parameterized logging**: Luôn dùng `{}` thay vì string concatenation

3. **Meaningful messages**: Viết log message rõ ràng, dễ hiểu

4. **Include context**: Thêm thông tin như userId, requestId khi cần thiết

5. **Don't log sensitive data**: Không log passwords, tokens, credit cards, etc.

6. **Performance**: Tránh log quá nhiều trong production, điều chỉnh log level phù hợp
