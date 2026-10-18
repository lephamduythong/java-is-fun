package com.example.springboottemplate.oauth;

import com.example.springboottemplate.config.OAuthConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MicrosoftOAuthHandler implements OAuthProviderClient {

    private final OAuthConfig config;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> stateStore = new ConcurrentHashMap<>();

    public MicrosoftOAuthHandler(OAuthConfig config) {
        this.config = config;
    }

    @Override
    public String getAuthorizationUrl() {
        String state = UUID.randomUUID().toString();
        stateStore.put(state, state);

        String scope = "openid profile email User.Read offline_access";

        try {
            StringBuilder url = new StringBuilder(config.getMicrosoftAuthUri());
            url.append("?client_id=").append(URLEncoder.encode(config.getMicrosoftClientId(), "UTF-8"));
            url.append("&redirect_uri=").append(URLEncoder.encode(config.getMicrosoftRedirectUri(), "UTF-8"));
            url.append("&response_type=code");
            url.append("&scope=").append(URLEncoder.encode(scope, "UTF-8"));
            url.append("&state=").append(URLEncoder.encode(state, "UTF-8"));
            url.append("&response_mode=query");
            url.append("&prompt=select_account");
            return url.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Microsoft authorization URL", e);
        }
    }

    @Override
    public boolean verifyState(String state) {
        return state != null && stateStore.remove(state) != null;
    }

    @Override
    public Map<String, Object> exchangeCodeForToken(String code) throws Exception {
        String postData = "code=" + URLEncoder.encode(code, "UTF-8")
                + "&client_id=" + URLEncoder.encode(config.getMicrosoftClientId(), "UTF-8")
                + "&client_secret=" + URLEncoder.encode(config.getMicrosoftClientSecret(), "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(config.getMicrosoftRedirectUri(), "UTF-8")
                + "&grant_type=authorization_code"
                + "&scope=" + URLEncoder.encode("openid profile email User.Read offline_access", "UTF-8");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getMicrosoftTokenUri()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(postData, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() == 200) {
            JsonNode json = objectMapper.readTree(response.body());

            System.out.println("Microsoft token exchange response: " + json.toString());


            Map<String, Object> result = new HashMap<>();
            result.put("access_token", json.get("access_token").asText());
            if (json.has("refresh_token")) result.put("refresh_token", json.get("refresh_token").asText());
            if (json.has("expires_in")) result.put("expires_in", json.get("expires_in").asInt());
            
            System.out.println("Microsoft token exchange result: " + result);
            
            return result;
        }
        throw new Exception("Microsoft token exchange failed: " + response.body());
    }

    @Override
    public Map<String, Object> getUserInfo(String accessToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getMicrosoftUserInfoUri()))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new Exception("Failed to get Microsoft user info. HTTP " + response.statusCode());
        }

        JsonNode json = objectMapper.readTree(response.body());
        Map<String, Object> userInfo = new HashMap<>();
        if (json.has("id")) userInfo.put("id", json.get("id").asText());
        if (json.has("displayName")) userInfo.put("name", json.get("displayName").asText());

        String email = firstNonBlank(
                json.path("mail").asText(null),
                json.path("userPrincipalName").asText(null),
                json.path("email").asText(null));
        if (email != null) userInfo.put("email", email);

        return userInfo;
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value;
            }
        }
        return null;
    }
}
