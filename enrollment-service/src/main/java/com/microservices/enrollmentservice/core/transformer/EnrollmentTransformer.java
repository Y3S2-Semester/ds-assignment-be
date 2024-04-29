package com.microservices.enrollmentservice.core.transformer;

import com.microservices.enrollmentservice.core.model.Enrollment;
import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.EnrollmentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentTransformer {

    public EnrollmentResponseDto transformEnrollmentDto(Enrollment enrollment) {
        EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
        enrollmentResponseDto.setId(enrollment.getId());
        enrollmentResponseDto.setCourse(null); // set course details using course id
        enrollmentResponseDto.setUser(null); // set user details using user id
        enrollmentResponseDto.setEnrollmentDate(enrollment.getEnrollmentDate());
        enrollmentResponseDto.setCompletionStatus(enrollment.getCompletionStatus());
        enrollmentResponseDto.setActive(enrollment.isActive());
        return enrollmentResponseDto;
    }

    public Enrollment reverseTransform(EnrollmentRequestDto enrollmentRequestDto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(enrollmentRequestDto.getCourseId());
        enrollment.setUserId(enrollmentRequestDto.getUserId());
        enrollment.setEnrollmentDate(enrollmentRequestDto.getEnrollmentDate());
        enrollment.setCompletionStatus(enrollmentRequestDto.getCompletionStatus());
        enrollment.setActive(enrollmentRequestDto.isActive());
        return enrollment;
    }
}
