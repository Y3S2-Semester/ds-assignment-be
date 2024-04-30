package com.microservices.contentservice.core.service.impl;

import com.microservices.contentservice.core.payload.ContentRequestDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import com.microservices.contentservice.core.repository.ContentRepository;
import com.microservices.contentservice.core.service.ContentService;
import com.microservices.contentservice.core.transformer.ContentTransformer;
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
    private final ContentRepository contentRepository;

    @NonNull
    private final MessageSource messageSource;

    @NonNull
    private final ContentTransformer contentTransformer;

    @Override
    public ResponseEntityDto addEnrollmentToCourse(ContentRequestDto contentRequestDto) {
        return new ResponseEntityDto(false, "contentResponseDto");
    }
}
