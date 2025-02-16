package com.demo.student.module.course.service;

import com.demo.student.module.course.entity.Course;
import com.demo.student.module.course.exception.CourseNotFoundException;
import com.demo.student.module.course.dto.Request.CreateCourseRequest;
import com.demo.student.module.course.dto.Response.CourseDetailsResponse;
import com.demo.student.module.course.repository.CourseRepository;
import com.demo.student.module.course.dto.Response.CourseResponse;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Cacheable("courses")
    public List<CourseResponse> getAvailableCourses() {
        return courseRepository.findAll().stream().map(CourseResponse::fromEntity).toList();
    }

    @Cacheable(value = "course", key = "#id")
    public CourseDetailsResponse getCourseById(String id) {
        return courseRepository.findById(id).map(CourseDetailsResponse::fromEntity)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));
    }

    public CourseDetailsResponse addCourse(CreateCourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setDuration(request.getDuration());
        course.setInstructor(request.getInstructor());
        course.setAvailableSeats(request.getAvailableSeats());

        Course savedCourse = courseRepository.save(course);
        return CourseDetailsResponse.fromEntity(savedCourse);
    }
}
