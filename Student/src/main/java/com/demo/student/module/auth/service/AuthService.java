package com.demo.student.module.auth.service;

import com.demo.student.module.auth.dto.request.RegisterRequest;
import com.demo.student.module.auth.util.jwt.JwtUtil;
import com.demo.student.module.user.repository.UserRepository;
import com.demo.student.module.user.entity.User;
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

    public void registerUser(RegisterRequest request) {

        try {
            var user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(request.getPassword());
            user.setCountryCode("");

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException("User already exists");
        }
    }

    public String authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return jwtUtil.generateToken(email, 5 * 60 * 1000);
        }
        throw new RuntimeException("Invalid Credentials");
    }

    public String generateRefreshToken(String email) {
        return jwtUtil.generateToken(email, 10 * 60 * 1000);
    }

    public String refreshAccessToken(String refreshToken) {
        String email = jwtUtil.extractEmail(refreshToken);
        return jwtUtil.generateToken(email, 5 * 60 * 1000);
    }}
