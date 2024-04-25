package com.microservices.courseservice.core.repository;

import com.microservices.courseservice.core.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollmentRepository extends MongoRepository <Enrollment, String> {
}
