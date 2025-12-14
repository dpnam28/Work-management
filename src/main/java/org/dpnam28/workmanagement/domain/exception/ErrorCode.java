package org.dpnam28.workmanagement.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("Internal server error", 500),
    USER_NOT_FOUND("User not found", 404),
    USER_ALREADY_EXISTS("User already exists", 409),
    FACULTY_NOT_FOUND("Faculty not found", 404),
    MAJOR_NOT_FOUND("Major not found", 404),
    STUDENT_NOT_FOUND("Student not found", 404),
    TEACHER_NOT_FOUND("Teacher not found", 404),
    SEMESTER_NOT_FOUND("Semester not found", 404),
    SUBJECT_NOT_FOUND("Subject not found", 404),
    CLASS_SUBJECT_NOT_FOUND("Class subject not found", 404),
    CLASS_SUBJECT_FULL("Class subject is full", 400),
    PLAN_NOT_FOUND("Plan not found", 404),
    TASK_NOT_FOUND("Task not found", 404),
    TASK_ASSIGNMENT_NOT_FOUND("Task assignment not found", 404),
    TASK_ALREADY_ASSIGNED("Task already assigned", 400),
    REPORT_NOT_FOUND("Report not found", 404),
    MEETING_NOT_FOUND("Meeting not found", 404),
    ENROLLMENT_NOT_FOUND("Enrollment not found", 404),
    SCHEDULE_NOT_FOUND("Schedule not found", 404),
    ENROLLMENT_ALREADY_EXISTS("Enrollment already exists", 409),
    INVALID_PASSWORD("Invalid password", 400),
    EMAIL_NOT_VALID("Email is not valid", 400),
    ARGUMENT_IS_REQUIRED("{arg} is required", 400),
    NOT_READABLE("Cannot deserialize value", 400),
    POSITION_IS_REQUIRED("Position is required", 400),
    ENTRY_YEAR_IS_REQUIRED("Entry year is required", 400),
    MAJOR_IS_REQUIRED("Major is required", 400),
    FACULTY_IS_REQUIRED("Faculty is required", 400),
    CODE_IS_REQUIRED("Code is required", 400),
    ASSIGNMENT_TARGET_INVALID("Assignment target is invalid", 400),
    FILE_FORMAT_INVALID("File format is invalid", 400),
    TOKEN_INVALID("Token is invalid", 400),
    TOKEN_EXPIRED("Token is expired", 403),
    ACCESS_DENIED("Access denied", 403),
    ENTITY_STATE_INVALID("Entity state invalid", 500),
    CODE_ALREADY_EXISTS("Code already exists", 409),
    FACULTY_NAME_ALREADY_EXISTS("Faculty name already exists", 409),
    SEMESTER_NAME_ALREADY_EXISTS("Semester name already exists", 409),
    SUBJECT_NAME_ALREADY_EXISTS("Subject name already exists", 409),
    SCHEDULE_TIME_ALREADY_EXISTS("Schedule time already exists", 409);
    private final String message;
    private final int code;

    ErrorCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public static ErrorCode fromMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message must not be null");
        }
        for (ErrorCode errorCode : values()) {
            if (errorCode.message.equalsIgnoreCase(message.trim())) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("No matching ErrorCode for message: " + message);
    }

    public String formatMessage(String arg) {
        if (arg == null) {
            return message;
        }
        return message.replace("{arg}", arg.trim());
    }
}
