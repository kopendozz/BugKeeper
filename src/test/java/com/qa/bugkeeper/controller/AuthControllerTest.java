package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private AuthController controller;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtUtil = mock(JwtUtil.class);
        controller = new AuthController(authenticationManager, jwtUtil);
    }

    @Test
    @DisplayName("Should authenticate and return JWT token")
    void shouldReturnTokenOnSuccessfulLogin() {
        // given
        String username = "testuser";
        String password = "secret";
        String fakeToken = "ey.fake.jwt";

        var request = new AuthController.LoginRequest(username, password);
        var expectedToken = new AuthController.TokenResponse(fakeToken);

        when(jwtUtil.generateToken(username)).thenReturn(fakeToken);

        // when
        ResponseEntity<AuthController.TokenResponse> response = controller.login(request);

        // then
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(jwtUtil).generateToken(username);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo(expectedToken);
    }
}
