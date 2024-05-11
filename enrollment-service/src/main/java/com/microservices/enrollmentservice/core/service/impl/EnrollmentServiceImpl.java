package com.microservices.enrollmentservice.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.enrollmentservice.core.exception.EntityNotFoundException;
import com.microservices.enrollmentservice.core.exception.ModuleException;
import com.microservices.enrollmentservice.core.feign.CourseServiceClient;
import com.microservices.enrollmentservice.core.feign.UserServiceClient;
import com.microservices.enrollmentservice.core.model.Enrollment;
import com.microservices.enrollmentservice.core.payload.CourseResponseDto;
import com.microservices.enrollmentservice.core.payload.EnrollmentRequestDto;
import com.microservices.enrollmentservice.core.payload.EnrollmentResponseDto;
import com.microservices.enrollmentservice.core.payload.UserResponseDto;
import com.microservices.enrollmentservice.core.payload.common.ResponseEntityDto;
import com.microservices.enrollmentservice.core.repository.EnrollmentRepository;
import com.microservices.enrollmentservice.core.service.EnrollmentService;
import com.microservices.enrollmentservice.core.transformer.EnrollmentTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.ALREADY_UN_ENROLLED;
import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.COURSE_NOT_FOUND;
import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.ENROLLMENT_NOT_FOUND;
import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.FAILED_TO_PASS_COURSE_DATA;
import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.USER_NOT_ENROLLED_TO_MODIFY;
import static com.microservices.enrollmentservice.core.common.EnrollmentServiceConstants.ApplicationMessages.USER_NOT_FOUND;

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

    private String getCurrentUserId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public ResponseEntityDto addEnrollmentToCourse(EnrollmentRequestDto enrollmentRequestDto) {
        String currentUserId = getCurrentUserId();
        CourseResponseDto course = getCourse(courseServiceClient.getCourseById(enrollmentRequestDto.getCourseId()));
        UserResponseDto user = (UserResponseDto) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        Enrollment savedEnrollment = enrollmentRepository.save(enrollmentTransformer.reverseTransform(enrollmentRequestDto, currentUserId));
        EnrollmentResponseDto enrollmentResponseDto = enrollmentTransformer.transformEnrollmentDto(savedEnrollment, course, user);
        return new ResponseEntityDto(false, enrollmentResponseDto);
    }

    @Override
    public ResponseEntityDto removeEnrollmentFromCourse(String enrollmentId) {
        String currentUserId = getCurrentUserId();
        Enrollment enrollment = getEnrollment(enrollmentId);
        if (!Objects.equals(enrollment.getUserId(), currentUserId)) {
            throw new ModuleException(messageSource.getMessage(USER_NOT_ENROLLED_TO_MODIFY, null, Locale.ENGLISH));
        }

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
        if (courseResponse.getBody() == null || courseResponse.getBody().getResults() == null) {
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
    public ResponseEntityDto getEnrolledCourseIdsByUserId(String userId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllByUserIdAndIsActiveTrue(userId);
        return new ResponseEntityDto(false, enrollments.stream().map(Enrollment::getCourseId).collect(Collectors.toSet()));
    }
}
