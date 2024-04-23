package com.microservices.contentservice.core.service.impl;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.repository.CourseRepository;
import com.microservices.contentservice.core.service.ContentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    @NonNull
    private final CourseRepository courseRepository;

    @NonNull
    private final MessageSource messageSource;

    @Override
    public ResponseEntityDto getAllContent(String courseId){
        return new ResponseEntityDto(false, "hello world");
    }
}
