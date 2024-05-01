package com.microservices.contentservice.core.service;

import com.microservices.contentservice.core.payload.ContentCreateDto;
import com.microservices.contentservice.core.payload.ContentUpdateDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;

public interface ContentService {

    ResponseEntityDto getAllContentByCourseId(String courseId);
    ResponseEntityDto addContentToCourse(String courseId, ContentCreateDto contentCreateDto);
    ResponseEntityDto updateContent(String id, ContentUpdateDto contentUpdateDto);
    ResponseEntityDto getContentById(String id);
    ResponseEntityDto archiveContent(String id);
}
