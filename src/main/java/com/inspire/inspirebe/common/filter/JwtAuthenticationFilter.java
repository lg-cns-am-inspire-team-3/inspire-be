package com.inspire.inspirebe.common.filter;

import com.inspire.inspirebe.common.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization header의 value 읽기
        String header = request.getHeader("Authorization");

        log.info("JwtAuthFilter Authorization : {}", header);

        if(header == null || !header.startsWith("Bearer ")) {
            log.info("JwtAuthFilter No Authorization Header");
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        log.info("JwtAuthFilter token : {}", token);

        if(!jwtProvider.validateAccessToken(token)) {
            log.info("JwtAuthFilter token accepted but invalid");
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtProvider.parseAccessToken(token);
        String loginId = claims.getSubject();
        String role = claims.get("role", String.class);

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        loginId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

        // request <-> Authentication.details 연결
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
