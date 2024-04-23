package com.microservices.contentservice.core.repository;

import com.microservices.contentservice.core.model.Content;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Content, String> {
}
