package com.demo.student.module.course.controller;

import com.demo.student.module.course.service.CourseRegistrationService;
import com.demo.student.module.course.dto.Request.CreateCourseRequest;
import com.demo.student.module.course.dto.Response.CourseDetailsResponse;
import com.demo.student.module.course.service.CourseService;
import com.demo.student.module.course.dto.Response.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course Management")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseRegistrationService courseRegistrationService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAvailableCourses() {
        return ResponseEntity.ok(courseService.getAvailableCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailsResponse> getCourseById(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseDetailsResponse> addCourse(@RequestBody CreateCourseRequest request) {
        return ResponseEntity.ok(courseService.addCourse(request));
    }

    @PostMapping("/{courseId}/register/{studentd}")
    public ResponseEntity<?> registerForCourse(@PathVariable String courseId, @PathVariable String studentId) {
        return courseRegistrationService.registerStudent(courseId, studentId);
    }

    @DeleteMapping("/{courseId}/cancel/{studentId}")
    public ResponseEntity<String> cancelCourseRegistration(@PathVariable String courseId, @PathVariable String studentId) {
        courseRegistrationService.cancelRegistration(courseId, studentId);
        return ResponseEntity.ok("Course registration canceled successfully");
    }

    @GetMapping("/enrolled/{studentId}")
    public ResponseEntity<List<CourseResponse>> getEnrolledCourses(@PathVariable String studentId) {
        return ResponseEntity.ok(courseRegistrationService.getEnrolledCourses(studentId));
    }
}

