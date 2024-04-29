package com.microservices.courseservice.core.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorDto {

    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String about;
    private String experience;
    private String socialMedia;
}
