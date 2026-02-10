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
@Setter
@ToString(exclude = "attendances")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Attend> attendances;

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
    public UserEntity(Long id, List<Attend> attendances, String email, String name, String contact, String address, UserRole role, UserStatus status, Integer salary) {
        this.id = id;
        this.attendances = attendances;
        this.email = email;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.role = role;
        this.status = status;
        this.salary = salary;
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

    public void activeUser() {
        this.status = UserStatus.ACTIVE;
    }
}
