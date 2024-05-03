package com.microservices.userservice.controller;

import com.microservices.userservice.core.model.User;
import com.microservices.userservice.core.payload.common.ResponseEntityDto;
import com.microservices.userservice.core.service.UserService;
import com.microservices.userservice.core.type.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private UserService userService;

    @GetMapping("me")
    public User getMe() {
        return userService.getMe();
    }

    @GetMapping("exists/{email}")
    public Boolean userExists(@PathVariable String email) {
        return userService.userExists(email);
    }

    @GetMapping("{id}")
    public ResponseEntityDto getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public ResponseEntityDto getAllUserByOptionalRole(@RequestParam Role role) {
        return userService.getAllUserByOptionalRole(role);
    }
}
