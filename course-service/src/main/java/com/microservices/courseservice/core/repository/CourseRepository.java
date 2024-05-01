package com.microservices.courseservice.core.repository;

import com.microservices.courseservice.core.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    Optional<Course> findByCourseName(String courseName);

    Iterable<Course> findByInstructorId(String instructorId);
}
