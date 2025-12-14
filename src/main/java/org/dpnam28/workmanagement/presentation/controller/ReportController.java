package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.security.AuthenticatedUser;
import org.dpnam28.workmanagement.presentation.dto.report.ReportRequest;
import org.dpnam28.workmanagement.presentation.dto.report.ReportResponse;
import org.dpnam28.workmanagement.presentation.dto.report.ReportUpdateRequest;
import org.dpnam28.workmanagement.usecase.ReportUseCase;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class ReportController {

    private final ReportUseCase reportUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ReportResponse> create(@Valid @ModelAttribute ReportRequest request,
                                              @RequestPart(value = "file", required = false) MultipartFile file,
                                              Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        ReportResponse response = reportUseCase.create(principal.getId(), request, file);
        return ApiResponse.apiResponseSuccess("Create report succeeded", response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ReportResponse> update(@PathVariable Long id,
                                              @Valid @ModelAttribute ReportUpdateRequest request,
                                              @RequestPart(value = "file", required = false) MultipartFile file,
                                              Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        ReportResponse response = reportUseCase.update(id, principal.getId(), request, file);
        return ApiResponse.apiResponseSuccess("Update report succeeded", response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> delete(@PathVariable Long id, Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        reportUseCase.delete(id, principal.getId());
        return ApiResponse.apiResponseSuccess("Delete report succeeded", null);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_TEACHER') and principal.position == T(org.dpnam28.workmanagement.domain.entity.PositionType).HEAD")
    public ApiResponse<List<ReportResponse>> getAll(Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        List<ReportResponse> responses = reportUseCase.getAll(principal.getId());
        return ApiResponse.apiResponseSuccess("Get reports succeeded", responses);
    }

    private AuthenticatedUser getPrincipal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser principal)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return principal;
    }
}
