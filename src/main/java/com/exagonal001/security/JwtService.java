package com.exagonal001.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String BEARER_PREFIX = "Bearer ";

    private final SecretKey signingKey;
    private final long expirationMs;
    private final String cookieName;
    private final String cookiePath;
    private final boolean cookieSecure;
    private final String cookieSameSite;

    public JwtService(JwtProperties jwtProperties) {
        this.signingKey = createKey(jwtProperties.secret());
        this.expirationMs = jwtProperties.expirationMs();
        this.cookieName = jwtProperties.cookieName() == null || jwtProperties.cookieName().isBlank() ? "jwt" : jwtProperties.cookieName();
        this.cookiePath = jwtProperties.cookiePath() == null || jwtProperties.cookiePath().isBlank() ? "/" : jwtProperties.cookiePath();
        this.cookieSecure = jwtProperties.cookieSecure();
        this.cookieSameSite = jwtProperties.cookieSameSite() == null || jwtProperties.cookieSameSite().isBlank() ? "Lax" : jwtProperties.cookieSameSite();
    }

    public String generateToken(UUID userId, String email, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)
                .claims(Map.of(
                        "uid", userId.toString(),
                        "role", role))
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getSubject() != null && claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public GrantedAuthority toAuthority(String role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }

        if (request.getCookies() == null) {
            return null;
        }

        for (var cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public ResponseCookie createAuthCookie(String token) {
        return ResponseCookie.from(cookieName, token)
                .httpOnly(true)
                .secure(cookieSecure)
                .path(cookiePath)
                .sameSite(cookieSameSite)
                .maxAge(expirationMs / 1000)
                .build();
    }

    public ResponseCookie createLogoutCookie() {
        return ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path(cookiePath)
                .sameSite(cookieSameSite)
                .maxAge(0)
                .build();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey createKey(String secret) {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (IllegalArgumentException ex) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}