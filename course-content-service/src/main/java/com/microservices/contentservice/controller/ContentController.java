package com.microservices.contentservice.controller;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.service.ContentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course-content")
@RequiredArgsConstructor
public class ContentController {

    @NonNull
    private final ContentService contentService;

    @GetMapping("of/{id}")
    public ResponseEntity<ResponseEntityDto> getAllContent(@PathVariable String id) {
        ResponseEntityDto response = contentService.getAllContent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
