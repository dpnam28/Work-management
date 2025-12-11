package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.TeachingAssignment;
import org.dpnam28.workmanagement.domain.entity.ClassSubject;
import org.dpnam28.workmanagement.domain.entity.Enrollment;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.security.AuthenticatedUser;
import org.dpnam28.workmanagement.presentation.dto.classsubject.ClassSubjectAssignTeacherRequest;
import org.dpnam28.workmanagement.presentation.dto.classsubject.ClassSubjectEnrollStudentRequest;
import org.dpnam28.workmanagement.presentation.dto.classsubject.ClassSubjectResponse;
import org.dpnam28.workmanagement.presentation.dto.classsubject.ClassSubjectStudentResponse;
import org.dpnam28.workmanagement.presentation.dto.classsubject.ClassSubjectTeacherResponse;
import org.dpnam28.workmanagement.presentation.mapper.ClassSubjectMapper;
import org.dpnam28.workmanagement.usecase.ClassSubjectUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/class-subjects")
@RequiredArgsConstructor
public class ClassSubjectController {

    private final ClassSubjectUseCase classSubjectUseCase;
    private final ClassSubjectMapper classSubjectMapper;

    @GetMapping("/semester/{semesterId}")
    public ApiResponse<List<ClassSubjectResponse>> getBySemester(@PathVariable Long semesterId) {
        List<ClassSubject> classSubjects = classSubjectUseCase.getBySemester(semesterId);
        List<ClassSubjectResponse> responses = classSubjects.stream()
                .map(classSubjectMapper::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.apiResponseSuccess("Fetch class subjects succeeded", responses);
    }

    @PostMapping("/{classSubjectId}/enroll")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ApiResponse<Object> enroll(@PathVariable Long classSubjectId, Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        classSubjectUseCase.enrollStudent(classSubjectId, principal.getId());
        return ApiResponse.apiResponseSuccess("Enroll succeeded", null);
    }

    @DeleteMapping("/{classSubjectId}/enroll")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ApiResponse<Object> drop(@PathVariable Long classSubjectId, Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        classSubjectUseCase.dropEnrollment(classSubjectId, principal.getId());
        return ApiResponse.apiResponseSuccess("Drop succeeded", null);
    }

    @PostMapping("/{classSubjectId}/enroll-student")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ApiResponse<Object> enrollStudent(@PathVariable Long classSubjectId,
                                             @RequestBody @Valid ClassSubjectEnrollStudentRequest request) {
        classSubjectUseCase.enrollStudent(classSubjectId, request.getStudentId());
        return ApiResponse.apiResponseSuccess("Enroll student succeeded", null);
    }

    @PostMapping("/{classSubjectId}/assign-teacher")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ApiResponse<Object> assignTeacher(@PathVariable Long classSubjectId,
                                             @RequestBody @Valid ClassSubjectAssignTeacherRequest request,
                                             Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        if (principal.getPosition() != PositionType.HEAD) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        classSubjectUseCase.assignTeacher(classSubjectId, request.getTeacherId());
        return ApiResponse.apiResponseSuccess("Assign teacher succeeded", null);
    }

    @GetMapping("/{classSubjectId}/students")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ApiResponse<List<ClassSubjectStudentResponse>> getEnrolledStudents(@PathVariable Long classSubjectId) {
        List<Enrollment> enrollments = classSubjectUseCase.getEnrolledStudents(classSubjectId);
        List<ClassSubjectStudentResponse> responses = enrollments.stream()
                .map(enrollment -> ClassSubjectStudentResponse.builder()
                        .studentId(enrollment.getStudent().getId())
                        .studentCode(enrollment.getStudent().getStudentCode())
                        .fullName(enrollment.getStudent().getUser().getFullName())
                        .email(enrollment.getStudent().getUser().getEmail())
                        .entryYear(enrollment.getStudent().getEntryYear())
                        .build())
                .collect(Collectors.toList());
        return ApiResponse.apiResponseSuccess("Fetch enrolled students succeeded", responses);
    }

    @GetMapping("/{classSubjectId}/teachers")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ApiResponse<List<ClassSubjectTeacherResponse>> getTeachers(@PathVariable Long classSubjectId) {
        List<TeachingAssignment> assignments = classSubjectUseCase.getClassSubjectTeachers(classSubjectId);
        List<ClassSubjectTeacherResponse> responses = assignments.stream()
                .map(assignment -> ClassSubjectTeacherResponse.builder()
                        .teacherId(assignment.getTeacher().getId())
                        .teacherCode(assignment.getTeacher().getTeacherCode())
                        .fullName(assignment.getTeacher().getUser().getFullName())
                        .position(assignment.getTeacher().getPosition())
                        .build())
                .collect(Collectors.toList());
        return ApiResponse.apiResponseSuccess("Fetch teachers succeeded", responses);
    }

    private AuthenticatedUser getPrincipal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser principal)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return principal;
    }
}
