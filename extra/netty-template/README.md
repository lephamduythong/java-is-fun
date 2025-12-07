# Netty HTTP Server với Google OAuth Login

Project Maven sử dụng Netty embedded server với các endpoints GET/POST và tính năng đăng nhập qua Google OAuth.

## Cấu trúc project

```
netty-template/
├── pom.xml
├── config.properties (cấu hình session storage)
├── config.properties.example
├── oauth.properties.example
├── .env.example
├── sessions.db (tạo tự động nếu dùng SQLite)
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── netty/
                        ├── NettyServerApplication.java
                        ├── HttpServerInitializer.java
                        ├── HttpServerHandler.java
                        ├── oauth/
                        │   ├── OAuthConfig.java
                        │   └── GoogleOAuthHandler.java
                        ├── config/
                        │   └── AppConfig.java
                        └── session/
                            ├── SessionManager.java
                            ├── Session.java
                            └── SessionDatabase.java
```

## Yêu cầu

- Java 11 trở lên
- Maven 3.6+
- Google OAuth Client ID và Secret (để sử dụng tính năng đăng nhập)

## Cấu hình Google OAuth

### 1. Tạo Google OAuth credentials

1. Truy cập [Google Cloud Console](https://console.cloud.google.com/)
2. Tạo hoặc chọn một project
3. Vào **APIs & Services** > **Credentials**
4. Click **Create Credentials** > **OAuth client ID**
5. Chọn **Web application**
6. Thêm **Authorized redirect URIs**: `http://localhost:8080/oauth/callback`
7. Lưu **Client ID** và **Client Secret**

### 2. Cấu hình credentials

**Cách 1: Sử dụng file properties**

Tạo file `oauth.properties` trong thư mục root:

```properties
google.client.id=YOUR_CLIENT_ID_HERE
google.client.secret=YOUR_CLIENT_SECRET_HERE
google.redirect.uri=http://localhost:8080/oauth/callback
```

**Cách 2: Sử dụng biến môi trường**

```bash
set GOOGLE_CLIENT_ID=your_client_id
set GOOGLE_CLIENT_SECRET=your_client_secret
set GOOGLE_REDIRECT_URI=http://localhost:8080/oauth/callback
```

### 3. Cấu hình Session Storage (Tùy chọn)

Mặc định, sessions được lưu trong **memory** (RAM). Để sử dụng **SQLite** cho persistent storage:

**Chỉnh sửa file `config.properties`:**
```properties
# Bật SQLite storage
session.storage.sqlite=true

# Đường dẫn file database (tùy chọn)
session.storage.sqlite.path=sessions.db

# Khoảng thời gian cleanup sessions hết hạn (giờ)
session.cleanup.interval.hours=1
```

**Lợi ích của SQLite:**
- Sessions được lưu vĩnh viễn, không mất khi restart server
- Tự động tạo file database theo cấu hình
- Cleanup tự động các sessions hết hạn theo interval đã cấu hình
- Đơn giản, chỉ cần sửa file config

## Cài đặt và chạy

### Cách 1: Chạy từ Maven (Development)

1. Build project:
```bash
mvn clean package
```

2. Chạy server:
```bash
mvn exec:java -Dexec.mainClass="com.example.netty.NettyServerApplication"
```

Hoặc chạy trực tiếp từ IDE bằng cách run class `NettyServerApplication`.

### Cách 2: Đóng gói thành JAR độc lập (Production)

1. Build JAR file với tất cả dependencies:
```bash
mvn clean package
```

Lệnh này sẽ tạo file `netty-server-standalone.jar` trong thư mục `target/`

2. Chạy JAR file:
```bash
java -jar target/netty-server-standalone.jar
```

**Lưu ý:** File JAR này bao gồm tất cả dependencies (Netty, Gson, Google OAuth, SQLite, BouncyCastle), có thể chạy trên bất kỳ máy nào có Java 11+ mà không cần cài đặt thêm.

**File cấu hình:**
- Đảm bảo file `config.properties` và `oauth.properties` nằm cùng thư mục với file JAR khi chạy
- Hoặc đặt trong classpath

**Ví dụ cấu trúc khi deploy:**
```
deploy/
├── netty-server-standalone.jar
├── config.properties
├── oauth.properties
└── sessions.db (tự động tạo nếu dùng SQLite)
```

Server sẽ khởi động trên port **8080**.

## Endpoints

### OAuth Endpoints

#### 1. GET /login
Khởi tạo quá trình đăng nhập với Google OAuth

**Sử dụng:**
```bash
# Mở trong browser
http://localhost:8080/login
```

Endpoint này sẽ redirect bạn đến trang đăng nhập của Google.

#### 2. GET /oauth/callback
Callback endpoint để nhận authorization code từ Google (tự động được Google gọi)

#### 3. GET /profile
Lấy thông tin profile của user đã đăng nhập

**Request:**
```bash
curl http://localhost:8080/profile \
  -H "Authorization: Bearer YOUR_SESSION_ID"
```

**Response:**
```json
{
  "status": "success",
  "user": {
    "id": "123456789",
    "email": "user@example.com",
    "name": "John Doe",
    "picture": "https://...",
    "verified_email": true
  },
  "sessionCreated": 1701950400000,
  "sessionExpires": 1702036800000
}
```

#### 4. GET /logout
Đăng xuất và xóa session

**Request:**
```bash
curl http://localhost:8080/logout \
  -H "Authorization: Bearer YOUR_SESSION_ID"
```

### API Endpoints (Protected - Requires Authentication)

#### 1. GET /hello

**Yêu cầu:** Phải đăng nhập với Google trước và cung cấp session ID trong **Authorization header**

**Request:**
```bash
curl http://localhost:8080/hello \
  -H "Authorization: Bearer YOUR_SESSION_ID"
```

**Response:**
```json
{
  "message": "Hello from Netty Server!",
  "method": "GET",
  "timestamp": 1701950400000,
  "status": "success",
  "user": "user@example.com"
}
```

**Nếu không có session hoặc session không hợp lệ:**
```json
{
  "error": "Unauthorized",
  "message": "Session ID required in Authorization header. Please login first at /login"
}
```

#### 2. POST /data

**Yêu cầu:** Phải đăng nhập với Google trước và cung cấp session ID trong **Authorization header**

**Request:**
```bash
curl -X POST http://localhost:8080/data \
  -H "Authorization: Bearer YOUR_SESSION_ID" \
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
  "status": "success",
  "user": "user@example.com"
}
```

**Nếu không có session hoặc session không hợp lệ:**
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired session. Please login again at /login"
}
```

## Dependencies

- **Netty 4.1.104.Final**: Framework bất đồng bộ cho network applications
- **Gson 2.10.1**: Xử lý JSON
- **Google OAuth Client 1.34.1**: Google OAuth authentication
- **Google HTTP Client 1.43.3**: HTTP client cho Google APIs
- **Google API Client 2.2.0**: Google API client library
- **SQLite JDBC 3.44.1.0**: SQLite database driver cho persistent session storage

## Tính năng

- ✅ Embedded Netty HTTP server
- ✅ GET endpoint trả về JSON (Protected)
- ✅ POST endpoint nhận và parse JSON (Protected)
- ✅ **Google OAuth 2.0 Login**
- ✅ **Session Management (24 giờ)**
- ✅ **Dual Session Storage**: In-memory hoặc SQLite persistent storage
- ✅ **Auto cleanup expired sessions** (mỗi giờ)
- ✅ **Protected Profile Endpoint**
- ✅ **Authentication Required for API Endpoints**
- ✅ Error handling
- ✅ Async non-blocking I/O

## Flow đăng nhập Google OAuth

1. User truy cập `/login`
2. Redirect đến Google login page
3. User đăng nhập và authorize
4. Google redirect về `/oauth/callback` với authorization code
5. Server exchange code để lấy access token
6. Server lấy user info từ Google
7. Server tạo session và trả về session ID
8. **User sử dụng session ID để truy cập protected endpoints (/hello, /data)**

## Bảo mật

- ✅ CSRF protection với state token
- ✅ Session expiration (24 giờ)
- ✅ Secure OAuth 2.0 flow
- ✅ Session-based authentication
- ✅ **Protected API endpoints - require Google login**

## Test OAuth Flow

1. Cấu hình Google OAuth credentials (xem phần cấu hình ở trên)
2. Khởi động server
3. Mở browser và truy cập: `http://localhost:8080/login`
4. Đăng nhập với Google account
5. Sau khi đăng nhập thành công, copy session ID từ response
6. Test các protected endpoints với session ID trong Authorization header:
   ```bash
   curl http://localhost:8080/hello -H "Authorization: Bearer YOUR_SESSION_ID"
   
   curl -X POST http://localhost:8080/data \
     -H "Authorization: Bearer YOUR_SESSION_ID" \
     -H "Content-Type: application/json" \
     -d '{"test":"data"}'
   
   curl http://localhost:8080/profile -H "Authorization: Bearer YOUR_SESSION_ID"
   
   curl http://localhost:8080/logout -H "Authorization: Bearer YOUR_SESSION_ID"
   ```

## Lưu ý

- Các endpoints `/hello`, `/data`, `/profile`, và `/logout` yêu cầu **session ID trong Authorization header**
- Format header: `Authorization: Bearer {SESSION_ID}` hoặc chỉ `Authorization: {SESSION_ID}`
- Nếu chưa đăng nhập hoặc session hết hạn, sẽ nhận được lỗi `401 Unauthorized`
- Session có hiệu lực trong 24 giờ sau khi đăng nhập

## Session Storage Options

### In-Memory Storage (Mặc định)
- Sessions lưu trong RAM
- Mất toàn bộ sessions khi restart server
- Nhanh và đơn giản
- Phù hợp cho development/testing

### SQLite Persistent Storage
- Sessions lưu trong file database (mặc định `sessions.db`)
- Giữ nguyên sessions sau khi restart server
- Tự động cleanup expired sessions theo interval cấu hình
- Phù hợp cho production

**Để bật SQLite storage, chỉnh sửa `config.properties`:**
```properties
session.storage.sqlite=true
session.storage.sqlite.path=sessions.db
session.cleanup.interval.hours=1
```

**Kiểm tra storage mode khi khởi động server:**
```
Configuration loaded from config.properties
SessionManager: Using SQLite for persistent session storage
SQLite database initialized successfully at: jdbc:sqlite:sessions.db
```
hoặc
```
Configuration loaded from config.properties
SessionManager: Using in-memory session storage
```
