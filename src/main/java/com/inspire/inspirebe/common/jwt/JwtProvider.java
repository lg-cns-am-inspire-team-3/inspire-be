package com.inspire.inspirebe.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final String accessSecret;
    private final String refreshSecret;
    private final long accessExpiration; // milliseconds
    private final long refreshExpiration; // milliseconds

    private Key accessKey;
    private Key refreshKey;

    public JwtProvider(@Value("${jwt.access.secret}") String accessSecret,
                       @Value("${jwt.refresh.secret}") String refreshSecret,
                       @Value("${jwt.access.expires}") long accessExpiration,
                       @Value("${jwt.refresh.expires}") long refreshExpiration) {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        log.info("access secret: {}", this.accessSecret);
        log.info("refresh secret: {}", this.refreshSecret);
        log.info("access expires: {}", this.accessExpiration);
        log.info("refresh secret: {}", this.refreshExpiration);
    }

    @PostConstruct
    protected void init() {
        this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
    }

    /*
     * access token 생성
     * sub: loginId
     * userId: PK
     * role: USER 또는 ADMIN
     */

    public String createAccessToken(Long userId, String role, long now) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + accessExpiration))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * refresh token 생성
     * sub: PK (access token과 목적을 달리함)
     */

    public String createRefreshToken(Long userId, long now) {

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + refreshExpiration))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * access token 및 refresh token 파서
     */

    public Claims parseAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims parseRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
     * access token 및 refresh token 검증
     */

    public boolean validateAccessToken(String token) {
        try {
            parseAccessToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            parseRefreshToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /*
     * claim 중 필요한 요소만 가져오기
     * 반드시 한 가지의 claim만 필요한 경우 사용
     */

    public Long getUserId(String token) {
        return Long.parseLong(parseAccessToken(token).getSubject());
    }

    public Long getUserIdFromRefresh(String token) {
        return Long.parseLong(parseRefreshToken(token).getSubject());
    }

    public String getRole(String token) {
        return parseAccessToken(token).get("role", String.class);
    }

    public Long getTokenExpiresInSeconds() {
        return this.accessExpiration / 1000;
    }
}
