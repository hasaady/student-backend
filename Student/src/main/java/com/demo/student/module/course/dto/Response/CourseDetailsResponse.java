package com.demo.student.module.course.dto.Response;

import com.demo.student.module.course.entity.Course;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailsResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String description;
    private int duration;
    private String instructor;
    private int availableSeats;

    public static CourseDetailsResponse fromEntity(Course course) {
        return new CourseDetailsResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getDuration(),
                course.getInstructor(),
                course.getAvailableSeats()
        );
    }
}