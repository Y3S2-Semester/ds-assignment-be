package com.microservices.contentservice.controller;

import com.microservices.contentservice.core.payload.ContentRequestDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.service.ContentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class ContentController {

    @NonNull
    private final ContentService contentService;

    @PostMapping()
    public ResponseEntity<ResponseEntityDto> enrollToCourse(@RequestBody ContentRequestDto contentRequestDto) {
        ResponseEntityDto response = contentService.addEnrollmentToCourse(contentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
