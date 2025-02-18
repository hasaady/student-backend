package com.demo.student.module.course.dto.Response;

import com.demo.student.module.course.entity.CourseRegistration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationResponse {
    private long id;
    private long courseId;
    private long userId;

    public static CourseRegistrationResponse fromEntity(CourseRegistration registration) {
        return new CourseRegistrationResponse(
                registration.getId(),
                registration.getCourseId(),
                registration.getUserId()
        );
    }
}