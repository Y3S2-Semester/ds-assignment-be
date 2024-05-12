package com.microservices.courseservice.controller;

import com.microservices.courseservice.core.payload.CourseRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class CourseController {

    @NonNull
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseEntityDto> addCourse(@Valid @RequestBody CourseRequestDto courseRequestDto) {
        ResponseEntityDto response = courseService.addCourse(courseRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseEntityDto> updateCourse(@Valid @RequestBody CourseRequestDto courseRequestDto) {
        ResponseEntityDto response = courseService.addCourse(courseRequestDto); // This should be courseService.updateCourse(courseDto)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityDto> getCourseById(@PathVariable String id) {
        ResponseEntityDto response = courseService.getCourseByCourseId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/course-name")
    public ResponseEntity<ResponseEntityDto> getCourseByCourseName(@RequestParam String courseName) {
        ResponseEntityDto response = courseService.getCourseByCourseName(courseName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseEntityDto> getAllCourse() {
        ResponseEntityDto response = courseService.getAllCourses();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/instructor")
    public ResponseEntity<ResponseEntityDto> getAllCourseByInstructorId(@RequestParam String instructorId) {
        ResponseEntityDto response = courseService.getCoursesByInstructor(instructorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
