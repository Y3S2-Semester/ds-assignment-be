package com.microservices.contentservice.core.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", getAuthToken());
    }

    private String getAuthToken() {
        return "Bearer " + JwtAuthenticationFilter.jwtToken.get();
    }
}
