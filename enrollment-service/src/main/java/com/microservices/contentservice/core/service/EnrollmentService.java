package com.microservices.contentservice.core.service;

import com.microservices.contentservice.core.payload.EnrollmentRequestDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;

public interface EnrollmentService {

    ResponseEntityDto addEnrollmentToCourse(EnrollmentRequestDto enrollmentRequestDto);

    ResponseEntityDto removeEnrollmentFromCourse(String enrollmentId);

    ResponseEntityDto getEnrollmentSummary(String enrollmentId);
}
