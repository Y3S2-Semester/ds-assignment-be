package com.microservices.enrollmentservice.core.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDto {
    private String id;
    private String name;
    private String email;
}
