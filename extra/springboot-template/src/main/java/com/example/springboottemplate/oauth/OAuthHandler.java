package com.example.springboottemplate.oauth;

import com.example.springboottemplate.config.OAuthConfig;
import com.example.springboottemplate.service.SessionService;
import com.example.springboottemplate.service.SessionService.SessionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthHandler {

    private final GoogleOAuthHandler googleOAuth;
    private final SessionService sessionService;
    private final OAuthConfig oauthConfig;

    public OAuthHandler(GoogleOAuthHandler googleOAuth, SessionService sessionService, OAuthConfig oauthConfig) {
        this.googleOAuth = googleOAuth;
        this.sessionService = sessionService;
        this.oauthConfig = oauthConfig;
    }

    public ResponseEntity<String> handleLoginRequest() {
        if (!oauthConfig.isConfigured()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE))
                    .body(wrapHtml("OAuth Not Configured",
                            "<h2>OAuth Not Configured</h2>"
                            + "<p>Google OAuth credentials are missing. "
                            + "Please set <code>google.client.id</code> and "
                            + "<code>google.client.secret</code> in "
                            + "<code>application.properties</code> or via environment variables "
                            + "<code>GOOGLE_CLIENT_ID</code> / <code>GOOGLE_CLIENT_SECRET</code>.</p>"));
        }
        try {
            String authUrl = googleOAuth.getAuthorizationUrl();

            String html = "<!DOCTYPE html>"
                    + "<html><head><title>Login with Google</title></head><body>"
                    + "<h2>Redirecting to Google Login...</h2>"
                    + "<p>If you are not redirected automatically, "
                    + "<a href=\"" + authUrl + "\">click here</a>.</p>"
                    + "<script>window.location.href = \"" + authUrl + "\";</script>"
                    + "</body></html>";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE))
                    .body(html);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE))
                    .body("<h2>Error</h2><p>Failed to generate login URL: " + e.getMessage() + "</p>");
        }
    }

    public ResponseEntity<String> handleOAuthCallback(String code, String state, String error) {
        try {
            if (error != null) {
                return htmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>Error: " + error + "</p>");
            }

            if (code == null) {
                return htmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>No authorization code received.</p>");
            }

            if (!googleOAuth.verifyState(state)) {
                return htmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>Invalid state token (CSRF protection).</p>");
            }

            Map<String, Object> tokenData = googleOAuth.exchangeCodeForToken(code);
            String accessToken = (String) tokenData.get("access_token");

            Map<String, Object> userInfo = googleOAuth.getUserInfo(accessToken);
            String sessionId = sessionService.createSession(userInfo);

            String html = "<h2>Login Successful!</h2>"
                    + "<p>Welcome, " + userInfo.get("name") + "!</p>"
                    + "<p>Email: " + userInfo.get("email") + "</p>"
                    + "<p>Your session will expire in 24 hours.</p>"
                    + "<br/>"
                    + "<p><a href='/test-api/profile'>View Profile</a> | "
                    + "<a href='/test-api/logout'>Logout</a></p>";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE))
                    .header("Set-Cookie", "SESSION=" + sessionId + "; HttpOnly; Path=/")
                    .body(wrapHtml("Login Successful", html));

        } catch (Exception e) {
            e.printStackTrace();
            return htmlResponse("Login Failed",
                    "<h2>Login Failed</h2><p>Error: " + e.getMessage() + "</p>");
        }
    }

    public ResponseEntity<?> handleProfileRequest(String sessionId) {
        SessionData session = sessionService.getSession(sessionId);
        if (session == null) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Unauthorized");
            err.put("message", "Invalid or expired session. Please log in again.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", "success");
        body.put("user", session.getUserInfo());
        body.put("sessionCreated", session.getCreatedAt().toString());
        body.put("sessionExpires", session.getExpiresAt().toString());
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<String> handleLogoutRequest(String sessionId) {
        sessionService.deleteSession(sessionId);
        return htmlResponse("Logged Out",
                "<h2>Logged Out Successfully</h2><p><a href='/test-api/login'>Login Again</a></p>");
    }

    // -------------------------------------------------------------------------

    private ResponseEntity<String> htmlResponse(String title, String body) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.TEXT_HTML_VALUE))
                .body(wrapHtml(title, body));
    }

    private String wrapHtml(String title, String body) {
        return "<!DOCTYPE html><html><head><title>" + title + "</title></head>"
                + "<body>" + body + "</body></html>";
    }
}
