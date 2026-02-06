package com.inspire.inspirebe.user.entity.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("ACTIVE"), SUSPENDED("SUSPENDED"), WITHDRAWN("WITHDRAWN");

    private final String code;

    UserStatus(String code) {
        this.code = code;
    }
}
