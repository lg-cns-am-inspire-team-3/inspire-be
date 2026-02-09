package com.inspire.inspirebe.user.entity;

import com.inspire.inspirebe.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
