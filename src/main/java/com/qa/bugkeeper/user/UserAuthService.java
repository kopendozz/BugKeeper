package com.qa.bugkeeper.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    public static final String ROLE_PREFIX = "ROLE_";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        var user = userRepository.getReferenceById(username);
        return new User(user.getUsername(), user.getPassword(), user.getEnabled(),
                true, true, true,
                Set.of(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole())));
    }
}
