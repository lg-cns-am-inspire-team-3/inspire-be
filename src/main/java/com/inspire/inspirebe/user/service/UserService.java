package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    // Create
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO);
    // Read
    public UserResponseDTO getUser(Long id);
    // Updates
    public void updateUser(Long id, UserUpdateDTO userUpdateDTO);
    // Delete
    public void deleteUser(Long id);
}
