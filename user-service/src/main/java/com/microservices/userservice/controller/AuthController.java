package com.microservices.userservice.controller;

import com.microservices.userservice.core.dto.SignUpRequest;
import com.microservices.userservice.core.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @NonNull
    private final AuthService authService;

    @PostMapping(value = "sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest.getEmail(), signUpRequest.getPassword());
    }

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String signIn(@RequestBody SignUpRequest signUpRequest) {
        return authService.signIn(signUpRequest.getEmail(), signUpRequest.getPassword());
    }
}
