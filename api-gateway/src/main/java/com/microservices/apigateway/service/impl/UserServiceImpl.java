package com.microservices.apigateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.apigateway.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @NonNull
    private final WebClient webClient;
    @NonNull
    private final ObjectMapper objectMapper;

    @Override
    public Flux<JsonNode> getCurrentUser(String token) {
        try {
            log.info("getCurrentUser: Execution started.");

            Scheduler blockingCompatibleScheduler = Schedulers.boundedElastic();
            Mono<Object> toBlock = webClient.post()
                    .uri("/api/v1/user/me")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .bodyToMono(Object.class);

            Mono<Object> wrapper = Mono.fromCallable(toBlock::block)
                    .subscribeOn(blockingCompatibleScheduler);

            return getMeInfo(token);
//        Object response = Mono.fromCallable(() ->
//                webClient.post()
//                        .uri("/api/v1/user/me")
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .header(HttpHeaders.AUTHORIZATION, token)
//                        .retrieve()
//                        .toEntity(String.class)
//                        .block()
//        ).subscribeOn(Schedulers.boundedElastic()).block();


//            return objectMapper.readTree(objectMapper.writeValueAsString(wrapper));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Flux<JsonNode> getMeInfo(String token) {
        return getMe(token)
                .flatMap(user -> {
                    JsonNode currentUser = getJson(user);
                    return Flux.just(currentUser);
                });
    }

    private Flux<Object> getMe(String token) {
        return webClient.get()
                .uri("/api/v1/user/me")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToFlux(Object.class);
    }

    JsonNode getJson(Object user) {
        try {
            return objectMapper.readTree(objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
