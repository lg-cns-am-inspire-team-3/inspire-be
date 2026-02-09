package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserCreateDTO;
import com.inspire.inspirebe.user.dto.UserResponseDTO;
import com.inspire.inspirebe.user.dto.UserUpdateDTO;
import com.inspire.inspirebe.user.entity.LocalCredentials;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.mapper.UserEntityMapper;
import com.inspire.inspirebe.user.repository.LocalCredentialsRepository;
import com.inspire.inspirebe.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        UserEntity userEntity = userRepository.findByIdWithPayments(id)
                .orElseThrow(EntityNotFoundException::new);

        return UserEntityMapper.toResponse(userEntity);
    }

    // Updates
    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userUpdateDTO.getPassword().ifPresent(password -> {
            LocalCredentials localCredentials = credentialsRepository.findByUserId(id)
                    .orElseThrow(EntityNotFoundException::new);
            localCredentials.changePasswordHash(passwordEncoder.encode(password));
        });

        userUpdateDTO.getName().ifPresent(userEntity::changeName);
        userUpdateDTO.getEmail().ifPresent(userEntity::changeEmail);
        userUpdateDTO.getContact().ifPresent(userEntity::changeContact);

    }

    // Delete
    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.delete(userEntity);
    }
}
