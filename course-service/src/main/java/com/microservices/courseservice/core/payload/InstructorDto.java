package com.microservices.courseservice.core.payload;

import com.microservices.courseservice.core.type.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorDto {


    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;

    private String about;

    private String experience;

    private String socialMedia;
}
