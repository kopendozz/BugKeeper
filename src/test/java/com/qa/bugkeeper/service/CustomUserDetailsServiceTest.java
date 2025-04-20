package com.qa.bugkeeper.service;

import com.qa.bugkeeper.entity.UserEntity;
import com.qa.bugkeeper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    UserRepository userRepository;
    CustomUserDetailsService service;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        service = new CustomUserDetailsService(userRepository);
    }

    @Nested
    class LoadUser {

        @Test
        void shouldReturnUserDetailsWhenUserExists() {
            var user = new UserEntity();
            user.setUsername("tester");
            user.setPassword("hashedpass");
            user.setEnabled(true);
            user.setRole("USER");

            when(userRepository.findByUsername("tester")).thenReturn(Optional.of(user));

            UserDetails result = service.loadUserByUsername("tester");

            assertThat(result.getUsername()).isEqualTo("tester");
            assertThat(result.getPassword()).isEqualTo("hashedpass");
            assertThat(result.isEnabled()).isTrue();
            assertThat(result.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");
        }

        @Test
        void shouldReturnDisabledUserDetails() {
            var user = new UserEntity();
            user.setUsername("inactive");
            user.setPassword("somepass");
            user.setEnabled(false);
            user.setRole("ADMIN");

            when(userRepository.findByUsername("inactive")).thenReturn(Optional.of(user));

            UserDetails result = service.loadUserByUsername("inactive");

            assertThat(result.isEnabled()).isFalse();
            assertThat(result.getAuthorities()).extracting("authority").containsExactly("ROLE_ADMIN");
        }

        @Test
        void shouldThrowWhenUserNotFound() {
            when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.loadUserByUsername("ghost"))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessageContaining("User not found: ghost");
        }
    }
}
