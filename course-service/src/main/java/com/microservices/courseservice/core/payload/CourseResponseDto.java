package com.microservices.courseservice.core.payload;

import com.microservices.courseservice.core.type.Category;
import com.microservices.courseservice.core.type.Status;
import lombok.Data;

@Data
public class CourseResponseDto {

    private String id;
    private String courseName;
    private String courseDescription;
    private InstructorResponseDto instructor;
    private Category category;
    private double price;
    private Status status;
    private boolean isActive;
}
