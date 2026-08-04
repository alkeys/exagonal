package com.exagonal001.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        String secret,
        long expirationMs,
        String cookieName,
        String cookiePath,
        boolean cookieSecure,
        String cookieSameSite) {
}