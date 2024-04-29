package com.microservices.enrollmentservice.core.repository;

import com.microservices.enrollmentservice.core.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollmentRepository extends MongoRepository <Enrollment, String> {
}
