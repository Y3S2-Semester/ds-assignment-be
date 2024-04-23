package com.microservices.courseservice.controller;

import com.microservices.courseservice.core.payload.CourseCreateRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.payload.dto.CourseDto;
import com.microservices.courseservice.core.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    @NonNull
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseEntityDto> addCourse(@RequestBody CourseDto courseDto) {
        return courseService.addCourse(courseDto);

    }

    @PutMapping
    public ResponseEntity<ResponseEntityDto> updateCourse(@RequestBody CourseDto courseDto) {
        return courseService.addCourse(courseDto);
    }

    @GetMapping("/getCourseById")
    public ResponseEntity<ResponseEntityDto> getCourseById(@RequestParam String courseId) {
        return courseService.getCourseByCourseId(courseId);
    }

    @GetMapping("/getCourseByCourseName")
    public ResponseEntity<ResponseEntityDto> getCourseByCourseName(@RequestParam String courseName) {
        return courseService.getCourseByCourseName(courseName);
    }
}
