package com.microservices.courseservice.controller;

import com.microservices.courseservice.core.payload.CourseCreateRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.payload.dto.CourseDto;
import com.microservices.courseservice.core.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    @NonNull
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseEntityDto> addCourse(@RequestBody CourseDto courseDto) {
        log.info("CourseController.addCourse() has been invoked");
        return courseService.addCourse(courseDto);
    }

    @PutMapping
    public ResponseEntity<ResponseEntityDto> updateCourse(@RequestBody CourseDto courseDto) {
        log.info("CourseController.updateCourse() has been invoked");
        return courseService.addCourse(courseDto); // This should be courseService.updateCourse(courseDto)
    }

    @GetMapping("/getCourseById")
    public ResponseEntity<ResponseEntityDto> getCourseById(@RequestParam String courseId) {
        log.info("CourseController.getCourseById() has been invoked");
        return courseService.getCourseByCourseId(courseId);
    }

    @GetMapping("/getCourseByCourseName")
    public ResponseEntity<ResponseEntityDto> getCourseByCourseName(@RequestParam String courseName) {
        log.info("CourseController.getCourseByCourseName() has been invoked");
        return courseService.getCourseByCourseName(courseName);
    }

    @GetMapping
    public ResponseEntity<ResponseEntityDto> getAllCourse() {
        log.info("CourseController.getAllCourse() has been invoked");
        return courseService.getAllCourses();
    }

    @GetMapping("/getAllCourseByInstructorId")
    public ResponseEntity<ResponseEntityDto> getAllCourseByInstructorId(@RequestParam String instructorId) {
        log.info("CourseController.getAllCourseByInstructorId() has been invoked");
        return courseService.getCoursesByInstructor(instructorId);
    }
}
