package com.demo.student.module.course.repository;

import com.demo.student.module.course.entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, String> {
    void deleteByCourseIdAndStudentId(String courseId, String studentId);
    List<CourseRegistration> findByStudentId(String studentId);
}

