package com.microservices.courseservice.core.service;

import com.microservices.courseservice.core.payload.CourseCreateRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;

public interface CourseService {

    ResponseEntityDto addCourse(CourseCreateRequestDto courseCreateRequestDto);
}
