package com.microservices.contentservice.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "progress")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Progress extends Auditable{

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(targetType = FieldType.STRING)
    @DBRef
    private String userId;

    @Field(targetType = FieldType.OBJECT_ID)
    private Content contentId;

    @Field(targetType = FieldType.STRING)
    private String courseId;

}
