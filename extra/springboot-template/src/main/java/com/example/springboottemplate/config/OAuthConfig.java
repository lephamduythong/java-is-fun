package com.example.springboottemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {

    @Value("${google.client.id:}")
    private String clientId;

    @Value("${google.client.secret:}")
    private String clientSecret;

    @Value("${google.redirect.uri:http://localhost:8080/test-api/oauth/callback}")
    private String redirectUri;

    @Value("${google.auth.uri:https://accounts.google.com/o/oauth2/v2/auth}")
    private String authUri;

    @Value("${google.token.uri:https://oauth2.googleapis.com/token}")
    private String tokenUri;

    @Value("${google.userinfo.uri:https://www.googleapis.com/oauth2/v2/userinfo}")
    private String userInfoUri;

    @Value("${microsoft.client.id:}")
    private String microsoftClientId;

    @Value("${microsoft.client.secret:}")
    private String microsoftClientSecret;

    @Value("${microsoft.redirect.uri:http://localhost:8080/test-api/oauth/callbackMicrosoft}")
    private String microsoftRedirectUri;

    @Value("${microsoft.auth.uri:https://login.microsoftonline.com/common/oauth2/v2.0/authorize}")
    private String microsoftAuthUri;

    @Value("${microsoft.token.uri:https://login.microsoftonline.com/common/oauth2/v2.0/token}")
    private String microsoftTokenUri;

    @Value("${microsoft.userinfo.uri:https://graph.microsoft.com/v1.0/me}")
    private String microsoftUserInfoUri;

    public String getClientId() { return clientId; }
    public String getClientSecret() { return clientSecret; }
    public String getRedirectUri() { return redirectUri; }
    public String getAuthUri() { return authUri; }
    public String getTokenUri() { return tokenUri; }
    public String getUserInfoUri() { return userInfoUri; }
    public String getMicrosoftClientId() { return microsoftClientId; }
    public String getMicrosoftClientSecret() { return microsoftClientSecret; }
    public String getMicrosoftRedirectUri() { return microsoftRedirectUri; }
    public String getMicrosoftAuthUri() { return microsoftAuthUri; }
    public String getMicrosoftTokenUri() { return microsoftTokenUri; }
    public String getMicrosoftUserInfoUri() { return microsoftUserInfoUri; }

    public boolean isConfigured() {
        return clientId != null && !clientId.isEmpty()
            && clientSecret != null && !clientSecret.isEmpty();
    }

    public boolean isMicrosoftConfigured() {
        return microsoftClientId != null && !microsoftClientId.isEmpty()
            && microsoftClientSecret != null && !microsoftClientSecret.isEmpty();
    }
}
