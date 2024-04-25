package com.microservices.apigateway.config;

import com.microservices.apigateway.filter.JwtAuthenticationFilter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfig {

    @Value("${address.base.user-service}")
    private String userServiceAddress;

    @Value("${address.base.course-service}")
    private String courseServiceAddress;

    @Value("${address.base.content-service}")
    private String contentServiceAddress;

    @NotNull
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        String replacement = "/${segment}";
        return builder.routes()
                .route(p -> p
                        .path("/user/**")
                        .filters(f -> f.rewritePath("/USER/(?<segment>.*)", replacement)
                                .filter(jwtAuthenticationFilter))
                        .uri(userServiceAddress))
                .route(p -> p
                        .path("/course/**")
                        .filters(f -> f.rewritePath("/COURSE/(?<segment>.*)", replacement)
                                .filter(jwtAuthenticationFilter))
                        .uri(courseServiceAddress))
                .route(p -> p
                        .path("/content/**")
                        .filters(f -> f.rewritePath("/CONTENT/(?<segment>.*)", replacement)
                                .filter(jwtAuthenticationFilter))
                        .uri(contentServiceAddress))
                .build();
    }
}
