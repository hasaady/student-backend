package com.demo.student.module.user.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String countryName;
}
