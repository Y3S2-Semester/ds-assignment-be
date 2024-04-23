package com.microservices.apigateway.config;

import com.microservices.apigateway.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ApiGatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/user-service/**")
                        .filters(f -> f.rewritePath("/user-service/(?<segment>.*)", "/${segment}")
                                .filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8000"))
                .route(p -> p
                        .path("/course-service/**")
                        .filters(f -> f.rewritePath("/course-service/(?<segment>.*)", "/${segment}").filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8100"))
                .build();
    }
}
