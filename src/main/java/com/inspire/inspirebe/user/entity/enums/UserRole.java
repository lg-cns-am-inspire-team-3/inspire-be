package com.inspire.inspirebe.user.entity.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"), ADMIN("ADMIN");

    private final String code;

    UserRole(String code) {
        this.code = code;
    }
}
