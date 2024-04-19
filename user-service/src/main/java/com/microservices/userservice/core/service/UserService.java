package com.microservices.userservice.core.service;

import com.microservices.userservice.core.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User getCurrentUser();

    User getMe();
}
