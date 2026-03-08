package com.example.springboottemplate.oauth;

import com.example.springboottemplate.config.OAuthConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GoogleOAuthHandler {

    private final OAuthConfig config;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> stateStore = new ConcurrentHashMap<>();

    public GoogleOAuthHandler(OAuthConfig config) {
        this.config = config;
    }

    /**
     * Generate Google OAuth authorization URL with CSRF state token.
     */
    public String getAuthorizationUrl() {
        String state = UUID.randomUUID().toString();
        stateStore.put(state, state);

        String scope = "email profile openid";

        try {
            StringBuilder url = new StringBuilder();
            url.append(config.getAuthUri());
            url.append("?client_id=").append(URLEncoder.encode(config.getClientId(), "UTF-8"));
            url.append("&redirect_uri=").append(URLEncoder.encode(config.getRedirectUri(), "UTF-8"));
            url.append("&response_type=code");
            url.append("&scope=").append(URLEncoder.encode(scope, "UTF-8"));
            url.append("&state=").append(URLEncoder.encode(state, "UTF-8"));
            url.append("&access_type=offline");
            url.append("&prompt=consent");

            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate authorization URL", e);
        }
    }

    /**
     * Verify the CSRF state token received from Google.
     */
    public boolean verifyState(String state) {
        return state != null && stateStore.remove(state) != null;
    }

    /**
     * Exchange authorization code for an access token.
     */
    public Map<String, Object> exchangeCodeForToken(String code) throws Exception {
        URL url = new URL(config.getTokenUri());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String postData = "code=" + URLEncoder.encode(code, "UTF-8")
                + "&client_id=" + URLEncoder.encode(config.getClientId(), "UTF-8")
                + "&client_secret=" + URLEncoder.encode(config.getClientSecret(), "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(config.getRedirectUri(), "UTF-8")
                + "&grant_type=authorization_code";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) response.append(line);
            }

            JsonNode json = objectMapper.readTree(response.toString());
            Map<String, Object> result = new HashMap<>();
            result.put("access_token", json.get("access_token").asText());
            if (json.has("refresh_token")) result.put("refresh_token", json.get("refresh_token").asText());
            if (json.has("expires_in"))    result.put("expires_in",    json.get("expires_in").asInt());
            return result;
        } else {
            StringBuilder err = new StringBuilder();
            try (BufferedReader r = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                String line;
                while ((line = r.readLine()) != null) err.append(line);
            }
            throw new Exception("Token exchange failed: " + err);
        }
    }

    /**
     * Retrieve user profile from Google using an access token.
     */
    public Map<String, Object> getUserInfo(String accessToken) throws Exception {
        URL url = new URL(config.getUserInfoUri());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) response.append(line);
            }

            JsonNode json = objectMapper.readTree(response.toString());
            Map<String, Object> userInfo = new HashMap<>();
            if (json.has("id"))             userInfo.put("id",             json.get("id").asText());
            if (json.has("email"))          userInfo.put("email",          json.get("email").asText());
            if (json.has("name"))           userInfo.put("name",           json.get("name").asText());
            if (json.has("picture"))        userInfo.put("picture",        json.get("picture").asText());
            if (json.has("verified_email")) userInfo.put("verified_email", json.get("verified_email").asBoolean());
            return userInfo;
        } else {
            throw new Exception("Failed to get user info. HTTP " + responseCode);
        }
    }
}
