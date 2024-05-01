package com.microservices.contentservice.core.repository;

import com.microservices.contentservice.core.model.Content;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContentRepository extends MongoRepository<Content, String> {
    List<Content> findAllByCourseId(String courseId);
}
