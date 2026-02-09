package com.inspire.inspirebe.auth.service;

public interface AuthService {

    void logout(String email);
    String login(String loginId);
}