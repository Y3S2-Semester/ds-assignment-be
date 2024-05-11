package com.microservices.contentservice.core.feign;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "course-service", url = "${course-service:http://localhost}:8100")
//@FeignClient(name = "course-service", url = "http://course-service:80")
@FeignClient(name = "course-service", url = "${feign.url.course}")
public interface CourseServiceClient {
    @GetMapping("/api/v1/course/{id}")
    ResponseEntity<ResponseEntityDto> getCourseById(@PathVariable String id);
}
