# Netty HTTP Server với Google OAuth Login

Project Maven sử dụng Netty embedded server với các endpoints GET/POST và tính năng đăng nhập qua Google OAuth.

## Cấu trúc project

```
netty-template/
├── pom.xml
├── oauth.properties.example
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
                        └── session/
                            ├── SessionManager.java
                            └── Session.java
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

#### 3. GET /profile?session={sessionId}
Lấy thông tin profile của user đã đăng nhập

**Request:**
```bash
curl "http://localhost:8080/profile?session=YOUR_SESSION_ID"
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

#### 4. GET /logout?session={sessionId}
Đăng xuất và xóa session

**Sử dụng:**
```bash
# Mở trong browser
http://localhost:8080/logout?session=YOUR_SESSION_ID
```

### API Endpoints (Original)

#### 1. GET /hello

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

#### 2. POST /data

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
- **Google OAuth Client 1.34.1**: Google OAuth authentication
- **Google HTTP Client 1.43.3**: HTTP client cho Google APIs
- **Google API Client 2.2.0**: Google API client library

## Tính năng

- ✅ Embedded Netty HTTP server
- ✅ GET endpoint trả về JSON
- ✅ POST endpoint nhận và parse JSON
- ✅ **Google OAuth 2.0 Login**
- ✅ **Session Management (24 giờ)**
- ✅ **Protected Profile Endpoint**
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
8. User sử dụng session ID để truy cập protected endpoints

## Bảo mật

- ✅ CSRF protection với state token
- ✅ Session expiration (24 giờ)
- ✅ Secure OAuth 2.0 flow
- ✅ Session-based authentication

## Test OAuth Flow

1. Cấu hình Google OAuth credentials (xem phần cấu hình ở trên)
2. Khởi động server
3. Mở browser và truy cập: `http://localhost:8080/login`
4. Đăng nhập với Google account
5. Sau khi đăng nhập thành công, copy session ID từ response
6. Test profile endpoint: `http://localhost:8080/profile?session=YOUR_SESSION_ID`
7. Logout: `http://localhost:8080/logout?session=YOUR_SESSION_ID`
