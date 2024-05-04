package com.microservices.userservice.core.payload;

import com.microservices.userservice.core.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter

public class SignInRequest {
    @NonNull
    String email;
    @NonNull
    String password;
}
