package com.microservices.enrollmentservice.core.transformer;

import com.microservices.enrollmentservice.core.model.Enrollment;
import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.EnrollmentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentTransformer {

    public EnrollmentResponseDto transformCourseDto(Enrollment enrollment) {
        EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
        return enrollmentResponseDto;
    }

    public Enrollment reverseTransform(EnrollmentRequestDto enrollmentRequestDto) {
        Enrollment enrollment = new Enrollment();
        return enrollment;
    }
}
