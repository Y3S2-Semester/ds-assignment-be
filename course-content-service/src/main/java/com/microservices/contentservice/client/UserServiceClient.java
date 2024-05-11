package com.microservices.contentservice.client;

import com.microservices.contentservice.core.payload.common.ResponseEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${feign.user-service.name}", url = "${feign.url.user}")
public interface UserServiceClient {
    @GetMapping(value = "/api/v1/user/{id}", produces = "application/json")
    ResponseEntityDto getUserById(@PathVariable("id") String id, @RequestHeader("Authorization") String token);
}
