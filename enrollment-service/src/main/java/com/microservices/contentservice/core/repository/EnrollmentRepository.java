package com.microservices.contentservice.core.repository;

import com.microservices.contentservice.core.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnrollmentRepository extends MongoRepository <Enrollment, String> {

    List<Enrollment> findAllByUserIdAndActiveTrue(String userId);
}
