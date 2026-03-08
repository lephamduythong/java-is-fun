package com.example.springboottemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {

    @Value("${google.client.id:}")
    private String clientId;

    @Value("${google.client.secret:}")
    private String clientSecret;

    @Value("${google.redirect.uri:http://localhost:5000/oauth/callback}")
    private String redirectUri;

    @Value("${google.auth.uri:https://accounts.google.com/o/oauth2/v2/auth}")
    private String authUri;

    @Value("${google.token.uri:https://oauth2.googleapis.com/token}")
    private String tokenUri;

    @Value("${google.userinfo.uri:https://www.googleapis.com/oauth2/v2/userinfo}")
    private String userInfoUri;

    public String getClientId() { return clientId; }
    public String getClientSecret() { return clientSecret; }
    public String getRedirectUri() { return redirectUri; }
    public String getAuthUri() { return authUri; }
    public String getTokenUri() { return tokenUri; }
    public String getUserInfoUri() { return userInfoUri; }

    public boolean isConfigured() {
        return clientId != null && !clientId.isEmpty()
            && clientSecret != null && !clientSecret.isEmpty();
    }
}
