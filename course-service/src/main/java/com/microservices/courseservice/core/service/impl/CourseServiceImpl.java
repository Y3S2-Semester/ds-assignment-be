package com.microservices.courseservice.core.service.impl;

import com.microservices.courseservice.core.model.Course;
import com.microservices.courseservice.core.payload.CourseRequestDto;
import com.microservices.courseservice.core.payload.CourseResponseDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.payload.fiegn.User;
import com.microservices.courseservice.core.payload.fiegn.UserResponseDto;
import com.microservices.courseservice.core.repository.CourseRepository;
import com.microservices.courseservice.core.service.CourseService;
import com.microservices.courseservice.core.service.cache.UserCache;
import com.microservices.courseservice.core.transformer.CourseTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    @NonNull
    private final CourseRepository courseRepository;

    @NonNull
    private final CourseTransformer courseTransformer;

    @NonNull
    private final UserCache userCache;

    @Override
    @Transactional
    public ResponseEntityDto addCourse(CourseRequestDto courseRequestDto) {
        try {
            log.info("CourseServiceImpl.addCourse() has been invoked");
            Course savedCourse = courseRepository.save(courseTransformer.reverseTransform(courseRequestDto));
            UserResponseDto userByUserId = userCache.getUserByUserId(savedCourse.getInstructorId());
            CourseResponseDto transformCourseResponseDto = courseTransformer.transformCourseDto(savedCourse, userByUserId);
            return new ResponseEntityDto(false, transformCourseResponseDto);
        } catch (Exception ex) {
            log.error("Error occurred while adding course: {}", ex.getMessage());
            return new ResponseEntityDto("Exception occurred", false);
        }
    }

    @Override
    public ResponseEntityDto getCourseByCourseId(String courseId) {
        log.info("CourseServiceImpl.getCourseByCourseId() has been invoked");
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            UserResponseDto userByUserId = userCache.getUserByUserId(optionalCourse.map(Course::getInstructorId).orElse(null));
            CourseResponseDto courseResponseDto = courseTransformer.transformCourseDto(optionalCourse.get(), userByUserId);
            return new ResponseEntityDto(false, courseResponseDto);
        } else {
            log.warn("Course with ID {} not found", courseId);
            return new ResponseEntityDto(true, "Course not found");
        }
    }

    @Override
    public ResponseEntityDto getCourseByCourseName(String courseName) {
        log.info("CourseServiceImpl.getCourseByCourseName() has been invoked");
        Optional<Course> optionalCourse = courseRepository.findByCourseName(courseName);
        if (optionalCourse.isPresent()) {
            UserResponseDto userByUserId = userCache.getUserByUserId(optionalCourse.map(Course::getInstructorId).orElse(null));
            CourseResponseDto courseResponseDto = courseTransformer.transformCourseDto(optionalCourse.get(), userByUserId);
            return new ResponseEntityDto(false, courseResponseDto);
        } else {
            log.warn("Course with name {} not found", courseName);
            return new ResponseEntityDto(true, "Course not found");
        }
    }

    @Override
    public ResponseEntityDto getAllCourses() {
        log.info("CourseServiceImpl.getAllCourses() has been invoked");
        List<CourseResponseDto> courseResponseDtos = new ArrayList<>();
        Iterable<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            UserResponseDto userByUserId = userCache.getUserByUserId(course.getInstructorId());
            courseResponseDtos.add(courseTransformer.transformCourseDto(course, userByUserId));
        }
        return new ResponseEntityDto(false, courseResponseDtos);
    }

    @Override
    public ResponseEntityDto getCoursesByInstructor(String instructorId) {
        log.info("CourseServiceImpl.getCoursesByInstructor() has been invoked");
        List<CourseResponseDto> courseResponseDtos = new ArrayList<>();
        Iterable<Course> courses = courseRepository.findByInstructorId(instructorId);
        for (Course course : courses) {
            UserResponseDto userByUserId = userCache.getUserByUserId(course.getInstructorId());
            courseResponseDtos.add(courseTransformer.transformCourseDto(course, userByUserId));
        }
        return new ResponseEntityDto(false, courseResponseDtos);
    }
}
