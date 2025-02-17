package com.demo.student.module.user.dto.request;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private String countryCode;
}
