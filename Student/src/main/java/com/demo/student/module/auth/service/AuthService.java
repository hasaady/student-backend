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

    public String authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

    public String generateRefreshToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public String refreshAccessToken(String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);
        return jwtUtil.generateToken(username);
    }
}
