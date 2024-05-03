package com.microservices.contentservice.controller;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service")
public interface CourseServiceClient {

    @GetMapping(value = "/api/v1/course/{id}", produces = "application/json")
    ResponseEntityDto getCourseById(@PathVariable String id);
}
