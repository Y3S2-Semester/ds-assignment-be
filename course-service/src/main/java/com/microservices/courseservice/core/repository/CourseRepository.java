package com.microservices.courseservice.core.repository;

import com.microservices.courseservice.core.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
}
