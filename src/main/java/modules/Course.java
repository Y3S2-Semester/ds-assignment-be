package modules;

import util.PreAuth;

public interface Course {

    @PreAuth("INSTRUCTOR")
    void addCourse();

    @PreAuth("INSTRUCTOR")
    void updateCourse();

    @PreAuth("INSTRUCTOR")
    void archiveCourse();

    @PreAuth("INSTRUCTOR")
    void getMyCoursesByInstructor();

    @PreAuth("LEARNER")
    void getMyCoursesByLearner();

    @PreAuth(value = {"LEARNER", "ADMIN", "INSTRUCTOR"})
    void getAllCourses();

    @PreAuth("LEARNER")
    void enrollToCourse();

    @PreAuth("LEARNER")
    void unEnrollFromCourse();

    @PreAuth("INSTRUCTOR")
    void viewEnrollmentSummary();
}
