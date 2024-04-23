package com.microservices.courseservice.core.service.impl;

import com.microservices.courseservice.core.CourseTransformer;
import com.microservices.courseservice.core.model.Course;
import com.microservices.courseservice.core.payload.CourseCreateRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.payload.dto.CourseDto;
import com.microservices.courseservice.core.repository.CourseRepository;
import com.microservices.courseservice.core.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseTransformer courseTransformer;


    @Override
    public ResponseEntity<ResponseEntityDto> addCourse(CourseDto courseDto) {
        try {
            log.info("CourseServiceImpl.addCourse() has been invoked");
            CourseDto transformCourseDto = courseTransformer.transformCourseDto(courseRepository.save(courseTransformer.reverseTransform(courseDto)));
            return new ResponseEntity<>(new ResponseEntityDto(false,transformCourseDto), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(new ResponseEntityDto("Exception occurred",false), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseEntityDto> getCourseByCourseId(String courseId) {
        log.info("CourseServiceImpl.getCourseByCourseId() has been invoked");
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            CourseDto courseDto = courseTransformer.transformCourseDto(optionalCourse.get());
            return new ResponseEntity<>(new ResponseEntityDto(false, courseDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseEntityDto(true, "Course not found"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseEntityDto> getCourseByCourseName(String courseName) {
        log.info("CourseServiceImpl.getCourseByCourseName() has been invoked");
        Optional<Course> optionalCourse = courseRepository.findByCourseName(courseName);
        if (optionalCourse.isPresent()) {
            CourseDto courseDto = courseTransformer.transformCourseDto(optionalCourse.get());
            return new ResponseEntity<>(new ResponseEntityDto(false, courseDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseEntityDto(true, "Course not found"), HttpStatus.NOT_FOUND);
        }
    }

}
