package com.microservices.courseservice.core.transformer;

import com.microservices.courseservice.core.model.Course;
import com.microservices.courseservice.core.payload.CourseRequestDto;
import com.microservices.courseservice.core.payload.CourseResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CourseTransformer {

    public CourseResponseDto transformCourseDto(Course course) {
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(course.getCourseId() != null ? course.getCourseId() : null);
        courseResponseDto.setCourseName(course.getCourseName());
        courseResponseDto.setCategory(course.getCategory());
        courseResponseDto.setPrice(course.getPrice());
        courseResponseDto.setCourseDescription(course.getCourseDescription());
        courseResponseDto.setStatus(course.getStatus());
        courseResponseDto.setInstructor(course.getInstructorId());
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
//        course.setInstructorId(courseRequestDto.getInstructorDto()!= null ? courseRequestDto.getInstructorDto().getId():"Instructor not avalible");
        course.setActive(courseRequestDto.isActive());
        return course;
    }
}
