package com.demo.student.module.controller;

import com.demo.student.module.service.UserService;
import com.demo.student.module.dto.request.ProfileUpdateRequest;
import com.demo.student.module.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Tag(name = "User Management")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping
    public ProfileResponse getUserProfile(Authentication authentication) {
        String username = authentication.name();
        return userService.getUserProfile(username);
    }

    @PutMapping
    public ProfileResponse updateUserProfile(
            Authentication authentication,
            @RequestBody ProfileUpdateRequest request) {
        String username = authentication.name();

        return userService.updateUserProfile(username, request);
    }
}
