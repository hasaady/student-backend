package com.demo.student.module.course.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateCourseRequest {
    private String name;
    private String description;
    private int duration;
    private String instructor;
    private int availableSeats;
}
