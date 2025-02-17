package com.demo.student.module.course.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_registrations")
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseId;
    private String studentId;

    public CourseRegistration(String courseId, String studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }
}
