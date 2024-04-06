package com.bizremark.institution.common.constants;

public final class ErrorConstants {
    public final static String UNEXPECTED_ERROR = "Unexpected error occurred: %s";
    public final static String UNABLE_TO_ADD_STUDENT = "Unable to add student due to IgniteException: %s";
    public final static String INTERNAL_SERVER_ERROR = "Internal server error occurred";
    public final static String STUDENT_WITH_ID_DOES_NOT_EXISTS = "student with id %d does not exists";

    public final static String UNABLE_TO_ADD_COURSE = "Unable to add course due to IgniteException: %s";
    public final static String COURSE_WITH_ID_DOES_NOT_EXISTS = "Course with id %d does not exists";
    public final static String COURSE_WITH_TITLE_ALREADY_EXISTS = "Course with title %s already exists";


    public final static String INVALID_STUDENT_OR_COURSE_ID = "Invalid student or course id";
    public final static String COURSE_ALREADY_REGISTERED = "Course already registered";

    public final static String STUDENT_WITH_ROLL_NUMBER_ALREADY_EXISTS = "Student with %s roll number already exists";

}
