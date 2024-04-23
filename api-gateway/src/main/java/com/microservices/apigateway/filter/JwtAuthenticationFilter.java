package com.microservices.apigateway.filter;

import com.microservices.apigateway.exception.UnAuthorizedException;
import com.microservices.apigateway.util.JwtUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {
    @NotNull
    private final JwtUtils jwtUtil;
    @NotNull
    private final WebClient userServiceWebClient;
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
        if (RouteValidator.isSecured.test(exchange.getRequest())) {
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
                    if (!isUserExists(username, authHeader)) {
                        logger.severe("User does not exists");
                        throw new UnAuthorizedException("User does not exists");
                    }
                } else {
                    logger.severe("Token Expired");
                    throw new UnAuthorizedException("Token Expired");
                }
            } catch (Exception e) {
                logger.severe("invalid access...!");
                throw new UnAuthorizedException("un authorized access to application");
            }
        } else {
            logger.severe("Not Secured Endpoint to proceed");
            throw new UnAuthorizedException("Not Secured Endpoint to proceed");
        }
    }

    boolean isUserExists(String username, String token) {
        Mono<Boolean> userExistsResponse = userServiceWebClient.post()
                .uri("/exists/" + username)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(Boolean.class);

        AtomicBoolean userExist = new AtomicBoolean(false);
        userExistsResponse.subscribe(
                existingUser -> {
                    userExist.set(true);
                    logger.info("user exists");
                },
                error -> logger.severe(error.getMessage())
        );

        return userExist.get();
    }
}