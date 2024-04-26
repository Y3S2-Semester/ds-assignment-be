package com.microservices.courseservice.core.service;

import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.payload.CourseDto;
import org.springframework.http.ResponseEntity;

public interface CourseService {

    ResponseEntity<ResponseEntityDto> addCourse(CourseDto courseDto);

    ResponseEntity<ResponseEntityDto> getCourseByCourseId(String courseId);

    ResponseEntity<ResponseEntityDto> getCourseByCourseName(String courseName);

    ResponseEntity<ResponseEntityDto> getAllCourses();

    ResponseEntity<ResponseEntityDto> getCoursesByInstructor(String instructorId);
}
