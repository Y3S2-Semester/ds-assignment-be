package com.microservices.contentservice.core.service;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;

public interface ContentService {

    ResponseEntityDto getAllContent(String courseId);
}
