package com.microservices.contentservice.client;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "course-service", url = "${feign.url.course}")
public interface CourseServiceClient {

    @GetMapping(value = "/api/v1/course/{id}", produces = "application/json")
    ResponseEntityDto getCourseById(@PathVariable("id") String id);

    @GetMapping(value = "/api/v1/course/instructor", produces = "application/json")
    ResponseEntityDto getCourseByInstructorId(@RequestParam(value = "instructorId", required = false) String instructorId);

}
