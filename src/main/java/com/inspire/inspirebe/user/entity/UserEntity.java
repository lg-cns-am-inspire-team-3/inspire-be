package com.inspire.inspirebe.user.entity;

import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.common.entity.BaseEntity;
import com.inspire.inspirebe.user.entity.enums.UserRole;
import com.inspire.inspirebe.user.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "salary")
    private Integer salary;


    @Builder
    public UserEntity(Long id, String email, String name, String contact, String address, UserRole role, UserStatus status, Integer salary) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.role = role;
        this.status = status;
        this.salary = salary;
    }

    @PrePersist
    public void prePersist() {
        if(role == null) {
            role = UserRole.USER;
        }

        if(status == null) {
            status = UserStatus.SUSPENDED;
        }
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeContact(String contact) {
        this.contact = contact;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeSalary(Integer salary) {
        this.salary = salary;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeUserStatus(String status) {
        this.status = UserStatus.valueOf(status.toUpperCase());
    }

    /*
     * 로직이 좋아서 남겨둠
     * 추후 사용할 수도
    public void approve() {
        // 1. 이미 승인된 유저인지 검증 (안전장치)
        if (this.status == UserStatus.ACTIVE) {
            throw new IllegalStateException("이미 승인된 사용자입니다.");
        }

        // 2. 상태를 ACTIVE로 변경
        this.status = UserStatus.ACTIVE;
    }
     */
}
