package com.microservices.courseservice.controller;

import com.microservices.courseservice.core.payload.CourseCreateRequestDto;
import com.microservices.courseservice.core.payload.common.ResponseEntityDto;
import com.microservices.courseservice.core.service.CourseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    @NonNull
    private final CourseService courseService;

    @PostMapping()
    public ResponseEntity<ResponseEntityDto> sendRequestForPurchaseOrder(@RequestBody CourseCreateRequestDto courseCreateRequestDto) {
        ResponseEntityDto response = courseService.addCourse(courseCreateRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
