package com.inspire.inspirebe.common.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
@Getter
public class JwtProperties {
    private final KeyProperties access;
    private final KeyProperties refresh;

    @RequiredArgsConstructor
    @Getter
    public static class KeyProperties {
        private final String secret;
        private final int expires;
    }
}
