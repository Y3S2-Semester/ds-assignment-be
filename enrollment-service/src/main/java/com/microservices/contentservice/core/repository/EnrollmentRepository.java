package com.microservices.contentservice.core.repository;

import com.microservices.contentservice.core.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollmentRepository extends MongoRepository <Enrollment, String> {
}
