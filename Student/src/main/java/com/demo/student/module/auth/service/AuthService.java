package com.demo.student.module.auth.service;

import com.demo.student.module.auth.util.jwt.JwtUtil;
import com.demo.student.module.repository.UserRepository;
import com.demo.student.module.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(email);
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

    public String generateRefreshToken(String email) {
        return jwtUtil.generateToken(email);
    }

    public String refreshAccessToken(String refreshToken) {
        String email = jwtUtil.extractEmail(refreshToken);
        return jwtUtil.generateToken(email);
    }
}
