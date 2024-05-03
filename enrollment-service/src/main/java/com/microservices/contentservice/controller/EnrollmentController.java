package com.microservices.contentservice.controller;

import com.microservices.contentservice.core.payload.EnrollmentRequestDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.service.EnrollmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    @NonNull
    private final EnrollmentService enrollmentService;

    @PostMapping()
    public ResponseEntity<ResponseEntityDto> enrollToCourse(@RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        ResponseEntityDto response = enrollmentService.addEnrollmentToCourse(enrollmentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseEntityDto> unEnrollFromCourse(@PathVariable String id) {
        ResponseEntityDto response = enrollmentService.removeEnrollmentFromCourse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntityDto> enrollmentSummary(@PathVariable String id) {
        ResponseEntityDto response = enrollmentService.getEnrollmentSummary(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}