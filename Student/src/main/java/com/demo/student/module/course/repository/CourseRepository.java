package com.demo.student.module.course.repository;

import com.demo.student.module.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByAvailableSeatsGreaterThan(int seats);
}
