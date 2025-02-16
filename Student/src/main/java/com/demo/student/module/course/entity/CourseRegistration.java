package com.demo.student.module.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_registrations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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
