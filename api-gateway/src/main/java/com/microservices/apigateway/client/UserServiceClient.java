package com.microservices.apigateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UserServiceClient {
    @Value("${address.base.user-service}")
    private String userServiceAddress;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(userServiceAddress).build();
    }
}

