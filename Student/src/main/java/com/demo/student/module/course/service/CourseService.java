package com.demo.student.module.course.service;

import com.demo.student.module.course.entity.Course;
import com.demo.student.module.course.exception.CourseNotFoundException;
import com.demo.student.module.course.dto.Request.CreateCourseRequest;
import com.demo.student.module.course.dto.Response.CourseDetailsResponse;
import com.demo.student.module.course.repository.CourseRepository;
import com.demo.student.module.course.dto.Response.CourseResponse;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

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

    public byte[] generateCourseSchedulePdf(long id) {

        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isEmpty()) {
            throw new CourseNotFoundException("Course not found");
        }

        Course course = courseOptional.get();
        String courseSchedule = course.getName() + " - " + course.getScheduleTime();
        var courseSchedules = List.of(courseSchedule);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Course Schedule").setBold().setFontSize(18));

            for (String schedule : courseSchedules) {
                document.add(new Paragraph(schedule));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
