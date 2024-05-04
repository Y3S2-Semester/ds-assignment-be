package com.microservices.contentservice.core.transformer;

import com.microservices.contentservice.core.model.Enrollment;
import com.microservices.contentservice.core.payload.CourseResponseDto;
import com.microservices.contentservice.core.payload.EnrollmentRequestDto;
import com.microservices.contentservice.core.payload.EnrollmentResponseDto;
import com.microservices.contentservice.core.payload.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentTransformer {

    public EnrollmentResponseDto transformEnrollmentDto(Enrollment enrollment, CourseResponseDto courseResponseDto, UserResponseDto userResponseDto) {
        EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
        enrollmentResponseDto.setId(enrollment.getId());
        enrollmentResponseDto.setCourse(courseResponseDto);
        enrollmentResponseDto.setUser(userResponseDto);
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
