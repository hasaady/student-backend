package com.demo.student.module.auth.service;

import com.demo.student.module.auth.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtUtil jwtUtil;

    public String generateAccessToken(String email) {
        return jwtUtil.generateToken(email, 5 * 60 * 1000);
    }

    public String generateRefreshToken(String email) {
        return jwtUtil.generateToken(email, 10 * 60 * 1000);
    }

}
