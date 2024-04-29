package com.microservices.enrollmentservice.core.service.impl;

import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import com.microservices.enrollmentservice.core.repository.EnrollmentRepository;
import com.microservices.enrollmentservice.core.service.EnrollmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    @NonNull
    private final EnrollmentRepository enrollmentRepository;

    @NonNull
    private final MessageSource messageSource;

    @Override
    public ResponseEntityDto addEnrollmentToCourse(EnrollmentRequestDto enrollmentRequestDto) {
        return new ResponseEntityDto(false, "enrolled to the course!");
    }

    @Override
    public ResponseEntityDto removeEnrollmentFromCourse(String enrollmentId) {
        return new ResponseEntityDto(false, "un enrolled from the course!");
    }

    @Override
    public ResponseEntityDto getEnrollmentSummary(String enrollmentId) {
        return new ResponseEntityDto(false, "enrollment summary!");
    }
}
