package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.security.AuthenticatedUser;
import org.dpnam28.workmanagement.presentation.dto.taskassignment.TaskAssignmentRequest;
import org.dpnam28.workmanagement.presentation.dto.taskassignment.TaskAssignmentResponse;
import org.dpnam28.workmanagement.usecase.TaskAssignmentUseCase;
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

@RestController
@RequestMapping("/task-assignments")
@RequiredArgsConstructor
public class TaskAssignmentController {

    private final TaskAssignmentUseCase taskAssignmentUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER') and principal.position == T(org.dpnam28.workmanagement.domain.entity.PositionType).HEAD")
    public ApiResponse<TaskAssignmentResponse> assign(@RequestBody @Valid TaskAssignmentRequest request,
                                                      Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        TaskAssignmentResponse response = taskAssignmentUseCase.assign(principal.getId(), request);
        return ApiResponse.apiResponseSuccess("Assign task succeeded", response);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public ApiResponse<List<TaskAssignmentResponse>> getMyAssignments(Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        List<TaskAssignmentResponse> responses = taskAssignmentUseCase.getTeacherAssignments(principal.getId());
        return ApiResponse.apiResponseSuccess("Get assignments succeeded", responses);
    }

    @GetMapping("/faculties/{facultyId}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER') and principal.position == T(org.dpnam28.workmanagement.domain.entity.PositionType).HEAD")
    public ApiResponse<List<TaskAssignmentResponse>> getFacultyAssignments(@PathVariable Long facultyId,
                                                                           Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        List<TaskAssignmentResponse> responses = taskAssignmentUseCase.getFacultyAssignments(principal.getId(), facultyId);
        return ApiResponse.apiResponseSuccess("Get faculty assignments succeeded", responses);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER') and principal.position == T(org.dpnam28.workmanagement.domain.entity.PositionType).HEAD")
    public ApiResponse<List<TaskAssignmentResponse>> getAll(Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        List<TaskAssignmentResponse> responses = taskAssignmentUseCase.getAllAssignments(principal.getId());
        return ApiResponse.apiResponseSuccess("Get task assignments succeeded", responses);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_TEACHER') and principal.position == T(org.dpnam28.workmanagement.domain.entity.PositionType).HEAD")
    public ApiResponse<Object> cancel(@PathVariable Long id, Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        taskAssignmentUseCase.cancel(principal.getId(), id);
        return ApiResponse.apiResponseSuccess("Cancel task assignment succeeded", null);
    }

    private AuthenticatedUser getPrincipal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser principal)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return principal;
    }
}
