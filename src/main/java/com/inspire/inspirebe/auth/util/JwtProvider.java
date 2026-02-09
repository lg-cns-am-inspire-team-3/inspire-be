package com.inspire.inspirebe.auth.util;

import com.inspire.inspirebe.user.entity.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    
    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.access.expires}")
    private long accessExpiration;

    @Value("${jwt.refresh.expires}")
    private long refreshExpiration;

    private Key key;

    @PostConstruct
    protected void init() {
   
        this.key = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
    }

   
    public String createAccessToken(String loginId, UserRole role) {
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("role", role.name()); 

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

  
    public String createRefreshToken(String loginId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}