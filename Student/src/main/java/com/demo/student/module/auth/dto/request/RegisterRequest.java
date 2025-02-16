package com.demo.student.module.auth.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
