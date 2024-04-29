package com.microservices.enrollmentservice.core.service;

import com.microservices.enrollmentservice.core.payload.CourseCreateRequestDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;

public interface CourseService {

    ResponseEntityDto addCourse(CourseCreateRequestDto courseCreateRequestDto);
}
