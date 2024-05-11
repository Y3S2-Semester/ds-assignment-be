package com.microservices.contentservice.client;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.enrollment-service.name}", url = "${feign.enrollment-service.url}")
public interface EnrollmentServiceClient {

    @GetMapping(value = "api/v1/enrollment/of/{userId}", produces = "application/json")
    ResponseEntityDto getEnrolledCourseIds(@PathVariable("userId") String userId);
}
