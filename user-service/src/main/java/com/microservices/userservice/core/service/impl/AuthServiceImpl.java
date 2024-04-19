package com.microservices.userservice.core.service.impl;

import com.microservices.userservice.core.model.User;
import com.microservices.userservice.core.repository.UserRepository;
import com.microservices.userservice.core.service.AuthService;
import com.microservices.userservice.core.service.JwtService;
import com.microservices.userservice.core.type.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public String signUp(String email, String password) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                return "Already exists";
            }
            var user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
            return jwtService.generateToken(user);
        } catch (Exception e) {
            log.error("SignUp: Error occurred: {}", e.getMessage());
        }
        return "Failed to sign up";
    }

    @Override
    public String signIn(String email, String password) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                return jwtService.generateToken(user.get());
            }
            return "Unauthorized";
        } catch (Exception e) {
            log.error("signIn: Error occurred: {}", e.getMessage());
        }
        return "Failed to sign in";
    }
}
