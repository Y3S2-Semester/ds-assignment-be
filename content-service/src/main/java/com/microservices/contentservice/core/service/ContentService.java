package com.microservices.contentservice.core.service;

import com.microservices.contentservice.core.payload.ContentRequestDto;
import com.microservices.contentservice.core.payload.common.ResponseEntityDto;

public interface ContentService {

    ResponseEntityDto addEnrollmentToCourse(ContentRequestDto contentRequestDto);
}
