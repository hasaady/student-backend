package com.demo.student.module.course.service;

import com.demo.student.module.course.repository.CourseRegistrationRepository;
import com.demo.student.module.course.repository.CourseRepository;
import com.demo.student.module.course.entity.Course;
import com.demo.student.module.course.entity.CourseRegistration;
import com.demo.student.module.course.exception.CourseNotFoundException;
import com.demo.student.module.course.dto.Response.CourseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRegistrationService {
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public CourseRegistrationService(CourseRepository courseRepository, CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRepository = courseRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public ResponseEntity<?> registerUserForCourse(long courseId, long userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course is not available"));

        if (course.getAvailableSeats() <= 0) {
            return ResponseEntity.badRequest().body("Course is full, no available seats.");
        }

        CourseRegistration registration = new CourseRegistration(courseId, userId);
        courseRegistrationRepository.save(registration);

        course.setAvailableSeats(course.getAvailableSeats() - 1);
        courseRepository.save(course);

        return ResponseEntity.ok("Registration successful. Remaining seats: " + course.getAvailableSeats());
    }

    public void cancelRegistration(long courseId, long userId) {
        courseRegistrationRepository.deleteByCourseIdAndUserId(courseId, userId);
    }

    public List<CourseResponse> getEnrolledCourses(long userId) {

        return courseRegistrationRepository
                .findByUserId(userId)
                .stream()
                .map(reg -> courseRepository.findById(reg.getCourseId()).map(CourseResponse::fromEntity).orElse(null))
                .filter(course -> course != null)
                .toList();
    }
}
