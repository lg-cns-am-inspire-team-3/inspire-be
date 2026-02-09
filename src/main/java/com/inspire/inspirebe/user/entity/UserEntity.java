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
@AllArgsConstructor 
@Builder 
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Attend> attendances;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "salary")
    private Integer salary;
}