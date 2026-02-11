package com.inspire.inspirebe.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class TempTest {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void createHash() {
        System.out.println(passwordEncoder.encode("admin"));
        System.out.println(passwordEncoder.encode("password1"));
        System.out.println(passwordEncoder.encode("password2"));
        System.out.println(passwordEncoder.encode("password3"));
        System.out.println(passwordEncoder.encode("password4"));
        System.out.println(passwordEncoder.encode("password5"));
        System.out.println(passwordEncoder.encode("password6"));
        System.out.println(passwordEncoder.encode("password7"));

    }
}
