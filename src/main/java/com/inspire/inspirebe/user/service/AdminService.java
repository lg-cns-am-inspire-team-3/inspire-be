package com.inspire.inspirebe.user.service;

import java.util.List;

import com.inspire.inspirebe.user.entity.UserEntity;

public interface AdminService {
    void approveUser(Long id, Integer salary);
    List<UserEntity> getAllUsers();
}
