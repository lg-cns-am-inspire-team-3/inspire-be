package com.inspire.inspirebe.common.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter  // 생성자 대신 Setter를 통해 값을 주입받도록 변경
public class JwtProperties {
    private KeyProperties access = new KeyProperties();
    private KeyProperties refresh = new KeyProperties();

    @Getter
    @Setter
    public static class KeyProperties {
        private String secret;
        private long expires;
    }
}