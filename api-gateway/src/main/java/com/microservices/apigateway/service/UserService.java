package com.microservices.apigateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;

public interface UserService {

    Flux<JsonNode> getCurrentUser(String token) throws InterruptedException;
}
