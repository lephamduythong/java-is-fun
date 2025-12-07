package com.example.netty.handler;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.UNAUTHORIZED;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.HashMap;
import java.util.Map;

import com.example.netty.common.WonderUtils;
import com.example.netty.oauth.GoogleOAuthHandler;
import com.example.netty.session.Session;
import com.example.netty.session.SessionManager;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.util.CharsetUtil;

public class OAuthHandler {
    private final GoogleOAuthHandler oauthHandler = GoogleOAuthHandler.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();

    public FullHttpResponse handleLoginRequest() {
        try {
            String authUrl = oauthHandler.getAuthorizationUrl();
            
            // Create HTML redirect response
            // "window.location.href" will redirect the user automatically to the Google login page
            String html = "<!DOCTYPE html>" +
                    "<html><head><title>Login with Google</title></head>" +
                    "<body>" +
                    "<h2>Redirecting to Google Login...</h2>" +
                    "<p>If you are not redirected automatically, <a href=\"" + authUrl + "\">click here</a>.</p>" +
                    "<script>window.location.href = \"" + authUrl + "\";</script>" +
                    "</body></html>";
            
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_1_1,
                    OK,
                    Unpooled.copiedBuffer(html, CharsetUtil.UTF_8)
            );
            
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            
            return response;
        } catch (Exception e) {
            return WonderUtils.createJsonResponse(
                INTERNAL_SERVER_ERROR, 
                WonderUtils.createErrorJson("Error", "Failed to generate login URL: " + e.getMessage())
            );
        }
    }

    public FullHttpResponse handleOAuthCallback(String uri) {
        try {
            // Parse query parameters
            Map<String, String> params = WonderUtils.parseQueryParams(uri);
            String code = params.get("code");
            String state = params.get("state");
            String error = params.get("error");
            
            if (error != null) {
                return WonderUtils.createHtmlResponse("Login Failed", 
                        "<h2>Login Failed</h2><p>Error: " + error + "</p>");
            }
            
            if (code == null) {
                return WonderUtils.createHtmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>No authorization code received</p>");
            }
            
            // Verify state token
            if (!oauthHandler.verifyState(state)) {
                return WonderUtils.createHtmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>Invalid state token (CSRF protection)</p>");
            }
            
            // Exchange code for token
            Map<String, Object> tokenData = oauthHandler.exchangeCodeForToken(code);
            String accessToken = (String) tokenData.get("access_token");
            
            // Get user info
            Map<String, Object> userInfo = oauthHandler.getUserInfo(accessToken);
            
            // Create session
            String sessionId = sessionManager.createSession(userInfo);
            
            // Create success HTML with user info
            String html = "<!DOCTYPE html>" +
                    "<html><head><title>Login Successful</title></head>" +
                    "<body>" +
                    "<h2>Login Successful!</h2>" +
                    "<p>Welcome, " + userInfo.get("name") + "!</p>" +
                    "<p>Email: " + userInfo.get("email") + "</p>" +
                    "<p>Session ID: " + sessionId + "</p>" +
                    "<p>Your session will expire in 24 hours.</p>" +
                    "<br/>" +
                    "<p><a href='/profile?session=" + sessionId + "'>View Profile</a> | " +
                    "<a href='/logout?session=" + sessionId + "'>Logout</a></p>" +
                    "</body></html>";
            
            return WonderUtils.createHtmlResponse("Login Successful", html);
            
        } catch (Exception e) {
            e.printStackTrace();
            return WonderUtils.createHtmlResponse("Login Failed",
                    "<h2>Login Failed</h2><p>Error: " + e.getMessage() + "</p>");
        }
    }

    public FullHttpResponse handleProfileRequest(FullHttpRequest request) {
        try {
            String sessionId = WonderUtils.getSessionFromHeader(request);
            
            if (sessionId == null) {
                return WonderUtils.createJsonResponse(UNAUTHORIZED,
                        WonderUtils.createErrorJson("Unauthorized", "Session ID required in Authorization header"));
            }
            
            Session session = sessionManager.getSession(sessionId);
            if (session == null) {
                return WonderUtils.createJsonResponse(UNAUTHORIZED,
                        WonderUtils.createErrorJson("Unauthorized", "Invalid or expired session"));
            }
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("user", session.getUserInfo());
            responseData.put("sessionCreated", session.getCreatedAt());
            responseData.put("sessionExpires", session.getExpiresAt());
            
            return WonderUtils.createJsonResponse(OK, responseData);
            
        } catch (Exception e) {
            return WonderUtils.createJsonResponse(INTERNAL_SERVER_ERROR,
                    WonderUtils.createErrorJson("Error", e.getMessage()));
        }
    }

    public FullHttpResponse handleLogoutRequest(FullHttpRequest request) {
        try {
            String sessionId = WonderUtils.getSessionFromHeader(request);
            
            if (sessionId != null) {
                sessionManager.deleteSession(sessionId);
            }
            
            return WonderUtils.createHtmlResponse("Logged Out",
                    "<h2>Logged Out Successfully</h2><p><a href='/login'>Login Again</a></p>");
            
        } catch (Exception e) {
            return WonderUtils.createJsonResponse(INTERNAL_SERVER_ERROR,
                    WonderUtils.createErrorJson("Error", e.getMessage()));
        }
    }
}
