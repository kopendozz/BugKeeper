package com.qa.bugkeeper.service;

import com.qa.bugkeeper.dto.UserDto;
import com.qa.bugkeeper.entity.UserEntity;
import com.qa.bugkeeper.exception.ResourceNotFoundException;
import com.qa.bugkeeper.mapper.UserMapper;
import com.qa.bugkeeper.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.qa.bugkeeper.service.UserService.USER_ALREADY_EXISTS;
import static com.qa.bugkeeper.service.UserService.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceTest extends BaseServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Nested
    class FindUser {

        @Test
        void shouldMapUserEntityToDto_whenUserExists() {
            //given
            var username = "username";
            var firstName = "John";
            var lastName = "Doe";
            var userEntity = UserEntity.builder()
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .enabled(true)
                    .build();
            var userDto = UserDto.builder()
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .enabled(true)
                    .build();
            when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
            when(userMapper.toDto(userEntity)).thenReturn(userDto);

            //when
            var user = userService.findUser(username);

            //then
            verify(userRepository).findByUsername(username);
            verify(userMapper).toDto(userEntity);

            Assertions.assertThat(user)
                    .as("User is not as expected.")
                    .isEqualTo(userDto);
            verifyNoMoreInteractions(userRepository, userMapper);
        }

        @Test
        void shouldThrowResourceNotFoundException_whenUserDoesNotExist() {
            //given
            var username = "username";
            when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

            //when & then
            assertThatThrownBy(() -> userService.findUser(username))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(USER_NOT_FOUND + username);

            verify(userRepository).findByUsername(username);
            verifyNoInteractions(userMapper);
        }
    }

    @Nested
    class CreateUser {

        @Test
        void shouldEncodePasswordAndSaveUser_whenCreatingNewUser() {
            //given
            var username = "username";
            var firstName = "John";
            var lastName = "Doe";
            var password = "secure_pass";
            var encodedPassword = "encoded_pass";
            var dto = UserDto.builder()
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .password(password)
                    .enabled(true)
                    .build();
            var user = UserEntity.builder()
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .enabled(true)
                    .password(encodedPassword)
                    .build();
            when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
            when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
            when(userMapper.toEntity(dto)).thenReturn(user);
            when(userRepository.save(user)).thenReturn(user);

            //when
            userService.createUser(dto);

            //then
            verify(userRepository).findByUsername(username);
            verify(passwordEncoder).encode(password);
            verify(userMapper).toEntity(dto);
            verify(userRepository).save(user);
            verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);
        }

        @Test
        void shouldThrowValidationException_whenUserAlreadyExists() {
            //given
            var username = "username";
            var dto = UserDto.builder().username(username).build();
            when(userRepository.findByUsername(username)).thenReturn(Optional.of(UserEntity.builder().build()));

            //when & then
            assertThatThrownBy(() -> userService.createUser(dto))
                    .isInstanceOf(ValidationException.class)
                    .hasMessage(USER_ALREADY_EXISTS.formatted(username));

            verify(userRepository).findByUsername(username);
            verifyNoInteractions(passwordEncoder);
            verifyNoInteractions(userMapper);
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    class DeactivateUser {

        @Test
        void shouldDisableUser_whenUserIsActive() {
            //given
            var username = "username";
            var user = UserEntity.builder().username(username).enabled(true).build();
            when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

            //when
            userService.deactivateUser(username);

            //then
            verify(userRepository).save(argThat(u -> !u.getEnabled()));
            verifyNoMoreInteractions(userRepository);
        }

        @Test
        void shouldNotSaveUser_whenAlreadyInactive() {
            //given
            var username = "username";
            var deactivatedUser = UserEntity.builder().username(username).enabled(false).build();
            when(userRepository.findByUsername(username)).thenReturn(Optional.of(deactivatedUser));

            //when
            userService.deactivateUser(username);

            //then
            verify(userRepository).findByUsername(username);
            verify(userRepository, never()).save(any());
            verifyNoMoreInteractions(userRepository);
        }
    }
}
