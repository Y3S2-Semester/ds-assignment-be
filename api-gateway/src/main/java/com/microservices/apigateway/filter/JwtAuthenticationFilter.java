package com.microservices.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;

@Component
@NoArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final String[] publicUrls = {
            "/api/v1/auth/**",
            "/api/v1/health",
            "/api/v1/course"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        if (isPublicUrl(requestUrl)) {
            return chain.filter(exchange);
        }

        String token = extractTokenFromHeaders(exchange.getRequest().getHeaders());
        if (token == null) {
            return unauthorized(exchange);
        }

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return chain.filter(exchange);
        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }

    private boolean isPublicUrl(String url) {
        for (String publicUrl : publicUrls) {
            if (pathMatcher.match(publicUrl, url)) {
                return true;
            }
        }
        return false;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private String extractTokenFromHeaders(HttpHeaders headers) {
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
