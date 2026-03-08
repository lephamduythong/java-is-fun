package com.example.springboottemplate.controller;

import com.example.springboottemplate.oauth.OAuthHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final OAuthHandler oauthHandler;

    public AuthController(OAuthHandler oauthHandler) {
        this.oauthHandler = oauthHandler;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return oauthHandler.handleLoginRequest();
    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<String> callback(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String error) {
        return oauthHandler.handleOAuthCallback(code, state, error);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(
            @CookieValue(name = "SESSION", required = false) String sessionId) {
        return oauthHandler.handleProfileRequest(sessionId);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue(name = "SESSION", required = false) String sessionId) {
        return oauthHandler.handleLogoutRequest(sessionId);
    }
}
