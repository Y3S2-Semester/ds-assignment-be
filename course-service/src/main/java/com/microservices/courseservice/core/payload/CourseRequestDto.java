package com.microservices.courseservice.core.payload;

import com.microservices.courseservice.core.type.Category;
import com.microservices.courseservice.core.type.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseRequestDto {
    private String courseName;
    private String courseDescription;
    private InstructorDto instructor;
    private Category category;
    private double price;
    private Status status;
    private boolean isActive = true;
}
