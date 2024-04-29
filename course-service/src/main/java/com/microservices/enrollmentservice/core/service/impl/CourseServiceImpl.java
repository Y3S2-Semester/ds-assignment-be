package com.microservices.enrollmentservice.core.service.impl;

import com.microservices.enrollmentservice.core.payload.CourseCreateRequestDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import com.microservices.enrollmentservice.core.repository.CourseRepository;
import com.microservices.enrollmentservice.core.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    @NonNull
    private final CourseRepository courseRepository;

    @NonNull
    private final MessageSource messageSource;

    @Override
    public ResponseEntityDto addCourse(CourseCreateRequestDto courseCreateRequestDto) {
        return new ResponseEntityDto(false, "hello world");
    }
}
