package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.config.TestMocks;
import com.qa.bugkeeper.dto.ProjectDto;
import com.qa.bugkeeper.entity.UserEntity;
import com.qa.bugkeeper.repository.UserRepository;
import com.qa.bugkeeper.security.JwtUtil;
import com.qa.bugkeeper.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestMocks.class)
class ProjectControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProjectService projectService;

    private String validToken;

    @BeforeEach
    void setUp() {
        var username = "testuser";
        var user = UserEntity.builder()
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .password(passwordEncoder.encode("pass"))
                .role("USER")
                .enabled(true)
                .build();
        userRepository.save(user);
        validToken = jwtUtil.generateToken(username);
    }

    @Test
    @DisplayName("GET /projects with valid JWT should return projects")
    void shouldReturnProjectsIfAuthorized() throws Exception {
        var expected = new ProjectDto(1L, "Secure Project");

        when(projectService.getAllProjects()).thenReturn(List.of(expected));

        mockMvc.perform(get("/projects")
                        .header("Authorization", "Bearer " + validToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()));
    }

    @Test
    @DisplayName("GET /projects with no JWT should return 401")
    void shouldRejectIfNoToken() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /projects with malformed JWT should return 401")
    void shouldRejectIfTokenIsMalformed() throws Exception {
        mockMvc.perform(get("/projects")
                        .header("Authorization", "Bearer not.a.jwt"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /projects with expired JWT should return 401")
    void shouldRejectIfTokenIsExpired() throws Exception {
        // simulate an expired token
        var expiredToken = jwtUtil.generateTokenWithExpiration(
                "testuser",
                new java.util.Date(System.currentTimeMillis() - 1000)
        );

        mockMvc.perform(get("/projects")
                        .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized());
    }
}
