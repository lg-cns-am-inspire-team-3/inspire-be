package com.inspire.inspirebe;

import com.inspire.inspirebe.common.jwt.JwtProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtPropertiesTest {

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void bindValuesFromYaml() {
        System.out.println(jwtProperties.getAccess().getSecret());
        System.out.println(jwtProperties.getAccess().getExpires());
        System.out.println(jwtProperties.getRefresh().getSecret());
        System.out.println(jwtProperties.getRefresh().getExpires());
    }
}
