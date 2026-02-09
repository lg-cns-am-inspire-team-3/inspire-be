package com.inspire.inspirebe.user.entity;

import com.inspire.inspirebe.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "local_credentials")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LocalCredentials extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Builder
    public LocalCredentials(Long id, Long userId, String passwordHash) {
        this.id = id;
        this.userId = userId;
        this.passwordHash = passwordHash;
    }


    public void changePasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
