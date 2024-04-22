package com.microservices.courseservice.core.service;

import com.microservices.courseservice.core.payload.EnrollmentRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;

public interface EnrollmentService {

    ResponseEntityDto addEnrollmentToCourse(EnrollmentRequestDto enrollmentRequestDto);

    ResponseEntityDto removeEnrollmentFromCourse(String enrollmentId);

    ResponseEntityDto getEnrollmentSummary(String enrollmentId);
}
