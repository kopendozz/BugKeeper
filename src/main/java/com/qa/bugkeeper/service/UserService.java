package com.qa.bugkeeper.service;

import com.qa.bugkeeper.entity.UserEntity;
import com.qa.bugkeeper.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createOrUpdateUser(UserEntity user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deactivateUser(String username) {
        var user = userRepository.getReferenceById(username);
        user.setEnabled(false);
        userRepository.save(user);
    }
}
