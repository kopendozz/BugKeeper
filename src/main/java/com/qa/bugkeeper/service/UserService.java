package com.qa.bugkeeper.service;

import com.qa.bugkeeper.entity.UserEntity;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    public static final String USER_NOT_FOUND = "User not found ";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + username));
    }

    public void createOrUpdateUser(UserEntity user) {
        var password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    public void deactivateUser(String username) {
        var user = findUser(username);
        if (BooleanUtils.isFalse(user.getEnabled())) return;
        user.setEnabled(false);
        userRepository.save(user);
    }
}
