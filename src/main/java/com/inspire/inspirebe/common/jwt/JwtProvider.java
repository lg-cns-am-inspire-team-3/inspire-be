package com.inspire.inspirebe.common.jwt;

import com.inspire.inspirebe.user.entity.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final String accessSecret;
    private final String refreshSecret;
    private final long accessExpiration; // milliseconds
    private final long refreshExpiration; // milliseconds

    private Key accessKey;
    private Key refreshKey;

    public JwtProvider(@Value("jwt.access.secret") String accessSecret,
                       @Value("jwt.refresh.secret") String refreshSecret,
                       @Value("jwt.access.expires") long accessExpiration,
                       @Value("jwt.refresh.expires") long refreshExpiration) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    @PostConstruct
    protected void init() {
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String loginId, UserRole role) {
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("role", role.name());

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + accessExpiration))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String createRefreshToken(String loginId) {

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + refreshExpiration))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
