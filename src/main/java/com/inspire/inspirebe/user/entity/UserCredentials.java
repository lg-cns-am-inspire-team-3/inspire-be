package com.inspire.inspirebe.user.entity;

import com.inspire.inspirebe.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_credentials")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserCredentials extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Builder
    public UserCredentials(Long id, Long userId, String loginId, String passwordHash) {
        this.id = id;
        this.userId = userId;
        this.loginId = loginId;
        this.passwordHash = passwordHash;
    }


    public void changePasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
