package modules;

import util.PreAuth;

public interface Content {
    @PreAuth("INSTRUCTOR")
    void viewLearnerProgress();

    @PreAuth("INSTRUCTOR")
    void addCourseContent();

    @PreAuth("INSTRUCTOR")
    void updateCourseContent();

    @PreAuth("INSTRUCTOR")
    void archiveCourseContent();

    @PreAuth("LEARNER")
    void getCourseContent();

    @PreAuth("LEARNER")
    void accessCourseContent();

    @PreAuth("ADMIN")
    void approveCourseContent();

    @PreAuth("ADMIN")
    void getPendingCourseContent();
}
