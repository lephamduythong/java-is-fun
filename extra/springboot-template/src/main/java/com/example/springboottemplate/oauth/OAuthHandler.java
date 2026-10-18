package com.example.springboottemplate.oauth;

import com.example.springboottemplate.config.OAuthConfig;
import com.example.springboottemplate.service.SessionService;
import com.example.springboottemplate.service.SessionService.SessionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthHandler {

    private static final MediaType HTML_UTF8 = new MediaType("text", "html", StandardCharsets.UTF_8);

    private final GoogleOAuthHandler googleOAuth;
    private final MicrosoftOAuthHandler microsoftOAuth;
    private final SessionService sessionService;
    private final OAuthConfig oauthConfig;

    public OAuthHandler(
            GoogleOAuthHandler googleOAuth,
            MicrosoftOAuthHandler microsoftOAuth,
            SessionService sessionService,
            OAuthConfig oauthConfig) {
        this.googleOAuth = googleOAuth;
        this.microsoftOAuth = microsoftOAuth;
        this.sessionService = sessionService;
        this.oauthConfig = oauthConfig;
    }

    public ResponseEntity<String> handleLoginRequest() {
        return handleLoginRequest("Google", googleOAuth, oauthConfig.isConfigured(),
                "google.client.id", "google.client.secret",
                "GOOGLE_CLIENT_ID", "GOOGLE_CLIENT_SECRET");
    }

    public ResponseEntity<String> handleMicrosoftLoginRequest() {
        return handleLoginRequest("Microsoft", microsoftOAuth, oauthConfig.isMicrosoftConfigured(),
                "microsoft.client.id", "microsoft.client.secret",
                "MICROSOFT_CLIENT_ID", "MICROSOFT_CLIENT_SECRET");
    }

    private ResponseEntity<String> handleLoginRequest(
            String provider,
            OAuthProviderClient providerOAuth,
            boolean configured,
            String clientIdProperty,
            String clientSecretProperty,
            String clientIdEnvironment,
            String clientSecretEnvironment) {
        if (!configured) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(HTML_UTF8)
                    .body(wrapHtml(provider + " OAuth Not Configured",
                            "<h2>" + provider + " OAuth Not Configured</h2>"
                                    + "<p>" + provider + " OAuth credentials are missing. "
                                    + "Please set <code>" + clientIdProperty + "</code> and "
                                    + "<code>" + clientSecretProperty + "</code> in "
                                    + "<code>application.properties</code> or via environment variables "
                                    + "<code>" + clientIdEnvironment + "</code> / <code>"
                                    + clientSecretEnvironment + "</code>.</p>"));
        }
        try {
            String authUrl = providerOAuth.getAuthorizationUrl();

            String html = "<!DOCTYPE html>"
                    + "<html><head><title>Login with " + provider + "</title></head><body>"
                    + "<h2>Redirecting to " + provider + " Login...</h2>"
                    + "<p>If you are not redirected automatically, "
                    + "<a href=\"" + authUrl + "\">click here</a>.</p>"
                    + "<script>window.location.href = \"" + authUrl + "\";</script>"
                    + "</body></html>";

            return ResponseEntity.ok()
                    .contentType(HTML_UTF8)
                    .body(html);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(HTML_UTF8)
                    .body("<h2>Error</h2><p>Failed to generate " + provider
                            + " login URL: " + e.getMessage() + "</p>");
        }
    }

    public ResponseEntity<String> handleOAuthCallback(String code, String state, String error) {
        return handleOAuthCallback("Google", googleOAuth, code, state, error,
                "/test-api/profile", "/test-api/logout");
    }

    public ResponseEntity<String> handleMicrosoftOAuthCallback(String code, String state, String error) {
        return handleOAuthCallback("Microsoft", microsoftOAuth, code, state, error,
                "/test-api/profileMicrosoft", "/test-api/logoutMicrosoft");
    }

    private ResponseEntity<String> handleOAuthCallback(
            String provider,
            OAuthProviderClient providerOAuth,
            String code,
            String state,
            String error,
            String profilePath,
            String logoutPath) {
        try {
            if (error != null) {
                return htmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>Error: " + error + "</p>");
            }

            if (code == null) {
                return htmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>No authorization code received.</p>");
            }

            if (!providerOAuth.verifyState(state)) {
                return htmlResponse("Login Failed",
                        "<h2>Login Failed</h2><p>Invalid state token (CSRF protection).</p>");
            }

            Map<String, Object> tokenData = providerOAuth.exchangeCodeForToken(code);
            String accessToken = (String) tokenData.get("access_token");

            Map<String, Object> userInfo = providerOAuth.getUserInfo(accessToken);
            String sessionId = sessionService.createSession(userInfo);

            String html = "<h2>Login Successful!</h2>"
                    + "<p>Welcome, " + userInfo.get("name") + "!</p>"
                    + "<p>Email: " + userInfo.get("email") + "</p>"
                    + "<p>Your session will expire in 24 hours.</p>"
                    + "<br/>"
                    + "<p><a href='" + profilePath + "'>View Profile</a> | "
                    + "<a href='" + logoutPath + "'>Logout</a></p>";

            return ResponseEntity.ok()
                    .contentType(HTML_UTF8)
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
        return handleLogoutRequest(sessionId, "/test-api/login");
    }

    public ResponseEntity<String> handleMicrosoftLogoutRequest(String sessionId) {
        return handleLogoutRequest(sessionId, "/test-api/loginMicrosoft");
    }

    private ResponseEntity<String> handleLogoutRequest(String sessionId, String loginPath) {
        sessionService.deleteSession(sessionId);
        return htmlResponse("Logged Out",
                "<h2>Logged Out Successfully</h2><p><a href='" + loginPath + "'>Login Again</a></p>");
    }

    // -------------------------------------------------------------------------

    private ResponseEntity<String> htmlResponse(String title, String body) {
        return ResponseEntity.ok()
                .contentType(HTML_UTF8)
                .body(wrapHtml(title, body));
    }

    private String wrapHtml(String title, String body) {
        return "<!DOCTYPE html><html><head><title>" + title + "</title></head>"
                + "<body>" + body + "</body></html>";
    }
}
