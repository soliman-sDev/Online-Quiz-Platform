package com.Quiz.quizPlatform.controller;

import com.Quiz.quizPlatform.dto.AuthResponse;
import com.Quiz.quizPlatform.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Register")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthResponse user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @Operation(summary = "Login and return JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthResponse request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
