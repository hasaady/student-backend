package com.demo.student.module.user.controller;

import com.demo.student.module.user.service.UserService;
import com.demo.student.module.user.dto.request.ProfileUpdateRequest;
import com.demo.student.module.user.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ProfileResponse getUserProfile(Authentication authentication) {
        String email = authentication.name();
        return userService.getUserProfile(email);
    }

    @PutMapping("/profile")
    public ProfileResponse updateUserProfile(
            Authentication authentication,
            @RequestBody ProfileUpdateRequest request) {
        String email = authentication.name();

        return userService.updateUserProfile(email, request);
    }
}
