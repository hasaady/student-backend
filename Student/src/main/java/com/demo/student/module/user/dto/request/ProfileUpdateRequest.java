package com.demo.student.module.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private String countryCode;
}
