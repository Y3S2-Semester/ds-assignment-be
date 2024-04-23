package com.microservices.contentservice.core.model;

import com.microservices.contentservice.core.type.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Document(collation = "content")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Content {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String courseId;

    @Field(targetType = FieldType.STRING)
    private ContentType contentType;

    @Field(targetType = FieldType.DATE_TIME)
    private Date publishedDate;

    @Field(targetType = FieldType.BOOLEAN)
    private Boolean visible;

}
