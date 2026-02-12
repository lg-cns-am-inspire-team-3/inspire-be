package com.inspire.inspirebe.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class TempTest {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void createHash() {
        System.out.println(passwordEncoder.encode("password6"));
        System.out.println(passwordEncoder.encode("password7"));
        System.out.println(passwordEncoder.encode("password8"));
        System.out.println(passwordEncoder.encode("password9"));
        System.out.println(passwordEncoder.encode("password10"));
        System.out.println(passwordEncoder.encode("password11"));
        System.out.println(passwordEncoder.encode("password12"));

    }
}
