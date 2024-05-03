package com.microservices.userservice.core.service;

import com.microservices.userservice.core.model.User;
import com.microservices.userservice.core.payload.common.ResponseEntityDto;
import com.microservices.userservice.core.type.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User getCurrentUser();

    User getMe();

    Boolean userExists(String username);

    ResponseEntityDto getUserById(String id);

    ResponseEntityDto getAllUserByOptionalRole(Role role);
}
