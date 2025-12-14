package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.security.AuthenticatedUser;
import org.dpnam28.workmanagement.presentation.dto.task.TaskRequest;
import org.dpnam28.workmanagement.presentation.dto.task.TaskResponse;
import org.dpnam28.workmanagement.presentation.dto.task.TaskUpdateRequest;
import org.dpnam28.workmanagement.usecase.TaskUseCase;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class TaskController {

    private final TaskUseCase taskUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<TaskResponse> create(@Valid @ModelAttribute TaskRequest request,
                                            @RequestPart(value = "file", required = false) MultipartFile file,
                                            Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        TaskResponse response = taskUseCase.create(principal.getId(), request, file);
        return ApiResponse.apiResponseSuccess("Create task succeeded", response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<TaskResponse> update(@PathVariable Long id,
                                            @Valid @ModelAttribute TaskUpdateRequest request,
                                            @RequestPart(value = "file", required = false) MultipartFile file,
                                            Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        TaskResponse response = taskUseCase.update(id, principal.getId(), request, file);
        return ApiResponse.apiResponseSuccess("Update task succeeded", response);
    }

    @PostMapping("/{id}/complete")
    public ApiResponse<TaskResponse> markAsDone(@PathVariable Long id,
                                                Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        TaskResponse response = taskUseCase.markAsDone(id, principal.getId());
        return ApiResponse.apiResponseSuccess("Complete task succeeded", response);
    }

    private AuthenticatedUser getPrincipal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser principal)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return principal;
    }
}
