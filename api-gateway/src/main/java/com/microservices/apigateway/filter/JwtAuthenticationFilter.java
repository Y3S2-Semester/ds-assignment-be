package com.microservices.apigateway.filter;

import com.microservices.apigateway.exception.UnAuthorizedException;
import com.microservices.apigateway.util.JwtUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {

    @NotNull
    private final JwtUtils jwtUtil;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final List<String> publicUrls = List.of(
            "/api/v1/auth/**",
            "/api/v1/health",
            "/api/v1/course"
    );

    Logger logger = Logger.getLogger("JwtAuthenticationFilter");

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        if (!isPublicUrl(requestUrl)) {
            validateRequestWithToken(exchange);
        }
        return chain.filter(exchange);
    }

    private boolean isPublicUrl(String url) {
        for (String publicUrl : publicUrls) {
            if (pathMatcher.match(publicUrl, url)) {
                return true;
            }
        }
        return false;
    }

    private void validateRequestWithToken(ServerWebExchange exchange) throws UnAuthorizedException {
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            logger.severe("missing authorization header");
            throw new UnAuthorizedException("missing authorization header");
        }

        String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        try {
            if (Boolean.FALSE.equals(jwtUtil.isTokenExpired(authHeader))) {
                String username = jwtUtil.extractUsername(authHeader);
                exchange.getRequest().mutate().header("userId", username);
                exchange.getRequest().mutate().header("role", jwtUtil.extractRole(authHeader));
            } else {
                logger.severe("Token Expired");
                throw new UnAuthorizedException("Token Expired");
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new UnAuthorizedException(e.getMessage());
        }
    }
}