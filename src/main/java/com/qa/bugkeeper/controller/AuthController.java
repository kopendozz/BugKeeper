package com.qa.bugkeeper.controller;

import com.qa.bugkeeper.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        authManager.authenticate(authToken);

        var token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    public record LoginRequest(String username, String password) {}
    public record TokenResponse(String token) {}
}
