package com.qa.bugkeeper.service;

import com.qa.bugkeeper.dto.UserDto;
import com.qa.bugkeeper.entity.UserEntity;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.mapper.UserMapper;
import com.qa.bugkeeper.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    public static final String USER_NOT_FOUND = "User not found ";
    public static final String USER_ALREADY_EXISTS = "User %s already exists.";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto findUser(String username) {
        var user = findUserEntity(username);
        return userMapper.toDto(user);
    }

    public void createUser(UserDto user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException(USER_ALREADY_EXISTS.formatted(user.getUsername()));
        }
        var password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        var userEntity = userMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    public void deactivateUser(String username) {
        var user = findUserEntity(username);
        if (BooleanUtils.isFalse(user.getEnabled())) return;
        user.setEnabled(false);
        userRepository.save(user);
    }

    private UserEntity findUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + username));
    }
}
