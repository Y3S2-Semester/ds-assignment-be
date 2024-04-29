package com.microservices.enrollmentservice.core.service.impl;

import com.microservices.enrollmentservice.core.exception.EntityNotFoundException;
import com.microservices.enrollmentservice.core.exception.ModuleException;
import com.microservices.enrollmentservice.core.model.Enrollment;
import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.EnrollmentResponseDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import com.microservices.enrollmentservice.core.repository.EnrollmentRepository;
import com.microservices.enrollmentservice.core.service.EnrollmentService;
import com.microservices.enrollmentservice.core.transformer.EnrollmentTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Locale;
import java.util.Optional;

import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.ALREADY_UN_ENROLLED;
import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.ENROLLMENT_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    @NonNull
    private final EnrollmentRepository enrollmentRepository;

    @NonNull
    private final MessageSource messageSource;

    @NonNull
    private final EnrollmentTransformer enrollmentTransformer;

    @Override
    public ResponseEntityDto addEnrollmentToCourse(EnrollmentRequestDto enrollmentRequestDto) {
        Enrollment savedEnrollment = enrollmentRepository.save(enrollmentTransformer.reverseTransform(enrollmentRequestDto));
        EnrollmentResponseDto enrollmentResponseDto = enrollmentTransformer.transformEnrollmentDto(savedEnrollment);
        return new ResponseEntityDto(false, enrollmentResponseDto);
    }

    @Override
    public ResponseEntityDto removeEnrollmentFromCourse(String enrollmentId) {
        Enrollment enrollment = getEnrollment(enrollmentId);
        if (!enrollment.isActive()) {
            throw new ModuleException(messageSource.getMessage(ALREADY_UN_ENROLLED, null, Locale.ENGLISH));
        }

        enrollment.setActive(false);
        Enrollment newSavedEnrollment = enrollmentRepository.save(enrollment);
        EnrollmentResponseDto enrollmentResponseDto = enrollmentTransformer.transformEnrollmentDto(newSavedEnrollment);
        return new ResponseEntityDto(false, enrollmentResponseDto);
    }

    @Override
    public ResponseEntityDto getEnrollmentSummary(String enrollmentId) {
        Enrollment enrollment = getEnrollment(enrollmentId);
        EnrollmentResponseDto enrollmentResponseDto = enrollmentTransformer.transformEnrollmentDto(enrollment);
        return new ResponseEntityDto(false, enrollmentResponseDto);
    }

    private Enrollment getEnrollment(String enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);
        if (optionalEnrollment.isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage(ENROLLMENT_NOT_FOUND, null, Locale.ENGLISH));
        }
        return optionalEnrollment.get();
    }
}
