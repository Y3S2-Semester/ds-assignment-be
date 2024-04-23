package com.microservices.courseservice.core.payload.dto;

import com.microservices.courseservice.core.Enum.Status;
import lombok.Data;

@Data
public class CourseDto {

    private String id;
    private String courseName;
    private String category;

    private Double price;

    private String courseDescription;

    private Status status;

    private InstructorDto instructorDto;

    private Boolean isActive;

}
