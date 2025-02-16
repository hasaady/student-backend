package com.demo.student.module.course.dto.Response;

import com.demo.student.module.course.entity.Course;
import lombok.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;

    public static CourseResponse fromEntity(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getName()
        );
    }
}