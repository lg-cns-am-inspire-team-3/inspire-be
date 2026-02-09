package com.inspire.inspirebe.auth.controller;

import com.inspire.inspirebe.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor 
public class AuthController {

    private final AuthService authService;

   
    @PostMapping("/login")
    public String login(@RequestParam String loginId) {
        
        return authService.login(loginId);
    }
}