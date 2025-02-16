package com.demo.student.module.auth.controller;

import com.demo.student.module.auth.service.AuthService;
import com.demo.student.module.auth.service.RefreshTokenService;
import com.demo.student.module.auth.dto.request.LoginRequest;
import com.demo.student.module.auth.dto.request.RegisterRequest;
import com.demo.student.module.auth.dto.response.LoginResponse;
import com.demo.student.module.auth.dto.response.RegisterResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok(new RegisterResponse("Registration Successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.authenticateUser(request.getEmail(), request.getPassword());
        String refreshToken = refreshTokenService.generateRefreshToken(request.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, refreshToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestParam String refreshToken) {
        String token = refreshTokenService.generateAccessToken(refreshToken);
        return ResponseEntity.ok(token);
    }
}
