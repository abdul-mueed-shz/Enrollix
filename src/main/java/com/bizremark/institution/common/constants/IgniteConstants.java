package com.bizremark.institution.common.constants;

public final class IgniteConstants {
    public final static String INSTITUTION_SCHEMA = "INSTITUTION";
    public final static String STUDENT_CACHE_NAME = "Student";
    public final static String COURSE_CACHE_NAME = "Course";

    public final static String COURSE_REGISTRATION_CACHE_NAME = "CourseRegistration";

    public final static String IGNITE_ADDRESS = "${IGNITE-ADDRESS}";

    public final static String IGNITE_INSTANCE_NAME = "${server.application.name}";

    public final static String IGNITE_INSTANCE_LABEL = "igniteInstance";
}
