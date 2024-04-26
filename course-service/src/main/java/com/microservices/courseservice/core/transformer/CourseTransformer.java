package com.microservices.courseservice.core.transformer;

import com.microservices.courseservice.core.model.Course;
import com.microservices.courseservice.core.payload.CourseDto;
import com.microservices.courseservice.core.payload.InstructorDto;
import org.springframework.stereotype.Component;

@Component
public class CourseTransformer {

    public CourseDto transformCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();

        courseDto.setId(course.getCourseId() != null ? course.getCourseId() : null);
        courseDto.setCourseName(course.getCourseName());
        courseDto.setCategory(course.getCategory());
        courseDto.setPrice(course.getPrice());
        courseDto.setCourseDescription(course.getCourseDescription());
        courseDto.setStatus(course.getStatus());
        //For the time being, if we can get to feign client implemented we can get the InstructorDto from the InstructorService
        courseDto.setInstructorDto(new InstructorDto(course.getInstructorId(),null,null,null,null,null,null,null));
        courseDto.setIsActive(course.getIsActive());
        return courseDto;
    }

    public Course reverseTransform(CourseDto courseDto) {
        Course course = new Course();
        course.setCourseId(courseDto.getId() != null ? courseDto.getId() : null);
        course.setCategory(courseDto.getCategory());
        course.setCourseName(courseDto.getCourseName());
        course.setPrice(courseDto.getPrice());
        course.setCourseDescription(courseDto.getCourseDescription());
        course.setStatus(courseDto.getStatus());
        //For the time being, it is hard Coded
        course.setInstructorId(courseDto.getInstructorDto()!= null ? courseDto.getInstructorDto().getId():"Instructor not avalible");
        course.setIsActive(courseDto.getIsActive());
        return course;
    }

}
