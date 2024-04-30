package com.microservices.contentservice.core.transformer;

import com.microservices.contentservice.core.model.Content;
import com.microservices.contentservice.core.payload.ContentRequestDto;
import com.microservices.contentservice.core.payload.ContentResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ContentTransformer {

    public ContentResponseDto transformContentDto(Content content) {
        ContentResponseDto contentResponseDto = new ContentResponseDto();
        return contentResponseDto;
    }

    public Content reverseTransform(ContentRequestDto contentRequestDto) {
        Content content = new Content();
        return content;
    }
}
