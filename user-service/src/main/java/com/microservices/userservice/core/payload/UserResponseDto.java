package com.microservices.userservice.core.payload;

import com.microservices.userservice.core.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    String id;
    String name;
    String email;
    Role role;
}
