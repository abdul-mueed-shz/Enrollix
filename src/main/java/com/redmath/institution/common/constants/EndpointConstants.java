package com.redmath.institution.common.constants;


public final class EndpointConstants {

    public static final String API_VER = "api/v1";
    public static final String STUDENT = "student";
    public static final String COURSE = "course";

    public static final String COURSE_REGISTRATION = "student/registration";
    public static final String COURSE_ID_PATH_QUERY = "{courseId}";
    public static final String COURSE_ID = "courseId";
    public static final String STUDENT_ID_PATH_QUERY = "{studentId}";
    public static final String STUDENT_ID = "studentId";

    private EndpointConstants() {
        throw new AssertionError();
    }
}
