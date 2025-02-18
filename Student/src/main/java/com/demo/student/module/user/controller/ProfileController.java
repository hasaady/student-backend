package com.demo.student.module.user.controller;

import com.demo.student.module.course.dto.Response.CourseResponse;
import com.demo.student.module.user.service.UserService;
import com.demo.student.module.user.dto.request.ProfileUpdateRequest;
import com.demo.student.module.user.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@Tag(name = "User Management")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("{userId}")
    public ProfileResponse getUserProfile(@PathVariable long userId) {
        return userService.getUserProfile(userId);
    }

    @PutMapping("{userId}")
    public ProfileResponse updateUserProfile(
            @PathVariable long userId,
            @RequestBody ProfileUpdateRequest request) {
        return userService.updateUserProfile(userId, request);
    }
}
