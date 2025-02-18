package com.demo.student.module.course.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courses", indexes = {
        @Index(name = "idxCoursesName", columnList = "name"),
        @Index(name = "idxCoursesInstructor", columnList = "instructor"),
        @Index(name = "idxCoursesScheduleTime", columnList = "scheduleTime")
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "schedule_time", nullable = false, columnDefinition = "TEXT")
    private String scheduleTime;

    @Column(name = "instructor", nullable = false)
    private String instructor;

    @Column(name = "available_seats", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int availableSeats;
}