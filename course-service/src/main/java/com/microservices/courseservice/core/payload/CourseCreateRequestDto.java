package com.microservices.courseservice.core.payload;

import com.microservices.courseservice.core.Enum.Status;
import com.microservices.courseservice.core.payload.dto.InstructorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequestDto {
    private String courseName;
    private String category;

    private Double price;

    private String courseDescription;

    private String instructorId;

    private Status status;

    private Boolean isActive;
}
