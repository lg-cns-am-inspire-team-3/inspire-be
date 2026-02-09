package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.repository.LocalCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LocalCredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    // Create
    @Override
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        return null;
    }

    // Read
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id) {
        return null;
    }

    // Updates
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {

    }

    // Delete
    @Override
    @Transactional
    public void deleteUser(Long id) {

    }
}
