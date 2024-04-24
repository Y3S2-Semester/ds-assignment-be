package com.microservices.courseservice.core.model;

import com.microservices.courseservice.core.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String courseId;

    private String courseName;

    private String category;

    private Double price;

    private String courseDescription;

    private String instructorId;

    private Status status;

    private Boolean isActive;

}