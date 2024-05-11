package com.microservices.contentservice.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.contentservice.core.exception.EntityNotFoundException;
import com.microservices.contentservice.core.exception.ModuleException;
import com.microservices.contentservice.core.feign.CourseServiceClient;
import com.microservices.contentservice.core.feign.UserServiceClient;
import com.microservices.contentservice.core.model.Enrollment;
import com.microservices.contentservice.core.payload.CourseResponseDto;
import com.microservices.contentservice.core.payload.EnrollmentRequestDto;
import com.microservices.contentservice.core.payload.EnrollmentResponseDto;
import com.microservices.contentservice.core.payload.UserResponseDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.repository.EnrollmentRepository;
import com.microservices.contentservice.core.service.EnrollmentService;
import com.microservices.contentservice.core.transformer.EnrollmentTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.microservices.contentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.ALREADY_UN_ENROLLED;
import static com.microservices.contentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.COURSE_NOT_FOUND;
import static com.microservices.contentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.ENROLLMENT_NOT_FOUND;
import static com.microservices.contentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.FAILED_TO_PASS_COURSE_DATA;
import static com.microservices.contentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.USER_NOT_FOUND;

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

    @NonNull
    private final CourseServiceClient courseServiceClient;

    @NonNull
    private final ObjectMapper mapper;

    @NonNull
    private final UserServiceClient userServiceClient;


    @Override
    public ResponseEntityDto addEnrollmentToCourse(EnrollmentRequestDto enrollmentRequestDto) {
        CourseResponseDto course = getCourse(courseServiceClient.getCourseById(enrollmentRequestDto.getCourseId()));
        UserResponseDto user = getUser(userServiceClient.getUserById(enrollmentRequestDto.getUserId()));
        Enrollment savedEnrollment = enrollmentRepository.save(enrollmentTransformer.reverseTransform(enrollmentRequestDto));
        EnrollmentResponseDto enrollmentResponseDto = enrollmentTransformer.transformEnrollmentDto(savedEnrollment, course, user);
        return new ResponseEntityDto(false, enrollmentResponseDto);
    }

    @Override
    public ResponseEntityDto removeEnrollmentFromCourse(String enrollmentId) {
        Enrollment enrollment = getEnrollment(enrollmentId);
        CourseResponseDto course = getCourse(courseServiceClient.getCourseById(enrollment.getCourseId()));
        UserResponseDto user = getUser(userServiceClient.getUserById(enrollment.getUserId()));

        if (!enrollment.isActive()) {
            throw new ModuleException(messageSource.getMessage(ALREADY_UN_ENROLLED, null, Locale.ENGLISH));
        }

        enrollment.setActive(false);
        Enrollment newSavedEnrollment = enrollmentRepository.save(enrollment);
        EnrollmentResponseDto enrollmentResponseDto = enrollmentTransformer.transformEnrollmentDto(newSavedEnrollment, course, user);
        return new ResponseEntityDto(false, enrollmentResponseDto);
    }

    @Override
    public ResponseEntityDto getEnrollmentSummary(String enrollmentId) {
        Enrollment enrollment = getEnrollment(enrollmentId);
        CourseResponseDto course = getCourse(courseServiceClient.getCourseById(enrollment.getCourseId()));
        UserResponseDto user = getUser(userServiceClient.getUserById(enrollment.getUserId()));
        EnrollmentResponseDto enrollmentResponseDto = enrollmentTransformer.transformEnrollmentDto(enrollment, course, user);
        return new ResponseEntityDto(false, enrollmentResponseDto);
    }

    private Enrollment getEnrollment(String enrollmentId) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);
        if (optionalEnrollment.isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage(ENROLLMENT_NOT_FOUND, null, Locale.ENGLISH));
        }
        return optionalEnrollment.get();
    }

    private CourseResponseDto getCourse(ResponseEntity<ResponseEntityDto> courseResponse) {
        if (courseResponse.getBody() != null && courseResponse.getBody().getResults() == null) {
            throw new EntityNotFoundException(messageSource.getMessage(COURSE_NOT_FOUND, null, Locale.ENGLISH));
        }

        CourseResponseDto courseResponseDto;
        try {
            courseResponseDto = mapper.convertValue(courseResponse.getBody().getResults().get(0), CourseResponseDto.class);
        } catch (IllegalArgumentException e) {
            throw new ModuleException(messageSource.getMessage(FAILED_TO_PASS_COURSE_DATA, null, Locale.ENGLISH));
        }

        return courseResponseDto;
    }

    private UserResponseDto getUser(ResponseEntityDto userResponse) {
        if (userResponse.getResults() == null) {
            throw new EntityNotFoundException(messageSource.getMessage(USER_NOT_FOUND, null, Locale.ENGLISH));
        }

        UserResponseDto userResponseDto;
        try {
            userResponseDto = mapper.convertValue(userResponse.getResults().get(0), UserResponseDto.class);
        } catch (IllegalArgumentException e) {
            throw new ModuleException(messageSource.getMessage(FAILED_TO_PASS_COURSE_DATA, null, Locale.ENGLISH));
        }

        return userResponseDto;
    }

    @Override
    public ResponseEntityDto getEnrollmentsByUserId(String userId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllByUserIdAndActiveTrue(userId);
        return new ResponseEntityDto(false, enrollments.stream().map(Enrollment::getCourseId).collect(Collectors.toSet()));
    }
}
