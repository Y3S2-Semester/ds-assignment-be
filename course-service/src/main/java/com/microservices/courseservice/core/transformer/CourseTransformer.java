package com.microservices.courseservice.core.transformer;

import com.microservices.courseservice.core.model.Course;
import com.microservices.courseservice.core.payload.CourseRequestDto;
import com.microservices.courseservice.core.payload.CourseResponseDto;
import com.microservices.courseservice.core.payload.InstructorResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CourseTransformer {

    public CourseResponseDto transformCourseDto(Course course, InstructorResponseDto instructorResponseDto) {
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(course.getCourseId() != null ? course.getCourseId() : null);
        courseResponseDto.setCourseName(course.getCourseName());
        courseResponseDto.setCategory(course.getCategory());
        courseResponseDto.setPrice(course.getPrice());
        courseResponseDto.setCourseDescription(course.getCourseDescription());
        courseResponseDto.setStatus(course.getStatus());
        courseResponseDto.setInstructor(instructorResponseDto);
        courseResponseDto.setActive(course.isActive());
        return courseResponseDto;
    }

    public Course reverseTransform(CourseRequestDto courseRequestDto) {
        Course course = new Course();
        course.setCategory(courseRequestDto.getCategory());
        course.setCourseName(courseRequestDto.getCourseName());
        course.setPrice(courseRequestDto.getPrice());
        course.setCourseDescription(courseRequestDto.getCourseDescription());
        course.setStatus(courseRequestDto.getStatus());
        course.setInstructorId(course.getInstructorId());
        course.setActive(courseRequestDto.isActive());
        return course;
    }
}
