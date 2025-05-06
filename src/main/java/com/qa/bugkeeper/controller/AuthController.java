package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController handles user authentication and token generation.
 * This controller provides endpoints for user login and JWT token issuance.
 * All endpoints are secured via JWT-based stateless authentication.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and JWT token generation")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user and return JWT",
            description = "Accepts username/password and returns a JWT for authenticated requests",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "JWT token is successfully generated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid username/password"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Invalid credentials"
                    )
            }
    )
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        authManager.authenticate(authToken);

        var token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @Schema(description = "Login request body containing username and password for authentication")
    public record LoginRequest(
            @Schema(description = "The username of the user", example = "kevin") String username,
            @Schema(description = "The password of the user", example = "password123") String password
    ) {}

    @Schema(description = "Response body containing the generated JWT token")
    public record TokenResponse(
            @Schema(description = "The generated JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            String token
    ) {}
}
