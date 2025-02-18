package com.demo.student.module.course.entity;

import com.demo.student.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courseRegistrations",
        indexes = {
                @Index(name = "idxCourseRegistrationsCourseId", columnList = "courseId"),
                @Index(name = "idxCourseRegistrationsUserId", columnList = "userId")
        }
)
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "course_id", nullable = false)
    private long courseId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    public CourseRegistration(long courseId, long userId) {
        this.courseId = courseId;
        this.userId = userId;
    }
}

