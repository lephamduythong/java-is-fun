package com.example.springboottemplate.oauth;

import java.util.Map;

public interface OAuthProviderClient {

    String getAuthorizationUrl();

    boolean verifyState(String state);

    Map<String, Object> exchangeCodeForToken(String code) throws Exception;

    Map<String, Object> getUserInfo(String accessToken) throws Exception;
}
