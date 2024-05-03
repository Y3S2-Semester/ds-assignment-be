package com.microservices.userservice.core.service.impl;

import com.microservices.userservice.core.exception.ModuleException;
import com.microservices.userservice.core.payload.SignUpRequest;
import com.microservices.userservice.core.model.User;
import com.microservices.userservice.core.payload.common.ResponseEntityDto;
import com.microservices.userservice.core.repository.UserRepository;
import com.microservices.userservice.core.service.AuthService;
import com.microservices.userservice.core.service.JwtService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final PasswordEncoder passwordEncoder;
    @NonNull
    private final JwtService jwtService;

    @Override
    public ResponseEntityDto signUp(SignUpRequest signUpRequest) {
        Optional<User> userOptional = userRepository.findByEmail(signUpRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new ModuleException("User already exists");
        }
        var user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .creationDate(LocalDate.now())
                .name(signUpRequest.getName())
                .build();
        try {
            user = userRepository.save(user);
            String token = jwtService.generateToken(user);
            return new ResponseEntityDto(false, token);
        } catch (Exception e) {
            log.error("SignUp: Error occurred: {}", e.getMessage());
            throw new ModuleException("Failed to sign up");
        }
    }

    @Override
    public ResponseEntityDto signIn(String email, String password) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                String token = jwtService.generateToken(user.get());
                return new ResponseEntityDto(false, token);
            }
            throw new AccessDeniedException("Unauthorized");
        } catch (Exception e) {
            log.error("signIn: Error occurred: {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }
    }
}
