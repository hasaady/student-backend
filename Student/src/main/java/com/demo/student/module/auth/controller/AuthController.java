package com.demo.student.module.auth.controller;

import com.demo.student.module.auth.dto.request.RegisterRequest;
import com.demo.student.module.auth.dto.response.RegisterResponse;
import com.demo.student.module.auth.service.AuthService;
import com.demo.student.module.auth.dto.request.LoginRequest;
import com.demo.student.module.auth.dto.response.LoginResponse;
import com.demo.student.module.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/auth")
@Tag(name = "User Auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return new RegisterResponse("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.authenticateUser(request.getUsername(), request.getPassword());
        String refreshToken = authService.generateRefreshToken(request.getUsername());
        return ResponseEntity.ok(new LoginResponse(token, refreshToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestParam String refreshToken) {
        String token = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(token);
    }

}
