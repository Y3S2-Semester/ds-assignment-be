package com.microservices.contentservice.client;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "course-service", url = "http://localhost:8100")
public interface CourseServiceClient {

    @GetMapping(value = "/api/v1/course/{id}", produces = "application/json")
    ResponseEntityDto getCourseById(@PathVariable("id") String id, @RequestHeader("Authorization") String token);
}
