package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.security.AuthenticatedUser;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanRequest;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanResponse;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanUpdateRequest;
import org.dpnam28.workmanagement.usecase.PlanUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class PlanController {

    private final PlanUseCase planUseCase;

    @PostMapping
    public ApiResponse<PlanResponse> create(@RequestBody @Valid PlanRequest request,
                                            Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        PlanResponse response = planUseCase.create(principal.getId(), request);
        return ApiResponse.apiResponseSuccess("Create plan succeeded", response);
    }

    @PutMapping("/{id}")
    public ApiResponse<PlanResponse> update(@PathVariable Long id,
                                            @RequestBody @Valid PlanUpdateRequest request,
                                            Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        PlanResponse response = planUseCase.update(id, principal.getId(), request);
        return ApiResponse.apiResponseSuccess("Update plan succeeded", response);
    }

    @PostMapping("/{id}/approve")
    public ApiResponse<PlanResponse> approve(@PathVariable Long id, Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        PlanResponse response = planUseCase.approve(id, principal.getId());
        return ApiResponse.apiResponseSuccess("Approve plan succeeded", response);
    }

    @PostMapping("/{id}/reject")
    public ApiResponse<PlanResponse> reject(@PathVariable Long id, Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        PlanResponse response = planUseCase.reject(id, principal.getId());
        return ApiResponse.apiResponseSuccess("Reject plan succeeded", response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER') and principal.position == T(org.dpnam28.workmanagement.domain.entity.PositionType).HEAD")
    public ApiResponse<List<PlanResponse>> getAll(Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        List<PlanResponse> responses = planUseCase.getAll(principal.getId());
        return ApiResponse.apiResponseSuccess("Get plans succeeded", responses);
    }

    private AuthenticatedUser getPrincipal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser principal)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return principal;
    }
}
