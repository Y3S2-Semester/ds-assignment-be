package com.microservices.contentservice.core.common;

public class EnrollmentServiceConstants {

    public interface ApplicationMessages {
        String ENROLLMENT_NOT_FOUND  = "api.error.enrollment.not.found";
        String ALREADY_UN_ENROLLED  = "api.error.already.un-enrolled";
        String COURSE_NOT_FOUND  = "api.error.course.not.found";
        String FAILED_TO_PASS_COURSE_DATA = "api.error.course.data.convert";
        String USER_NOT_FOUND = "api.error.user.not-found";
    }
}
