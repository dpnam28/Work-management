package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.security.AuthenticatedUser;
import org.dpnam28.workmanagement.presentation.dto.meeting.MeetingRequest;
import org.dpnam28.workmanagement.presentation.dto.meeting.MeetingResponse;
import org.dpnam28.workmanagement.presentation.dto.meeting.MeetingUpdateRequest;
import org.dpnam28.workmanagement.usecase.MeetingUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class MeetingController {

    private final MeetingUseCase meetingUseCase;

    @PostMapping
    public ApiResponse<MeetingResponse> create(@RequestBody @Valid MeetingRequest request,
                                               Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        MeetingResponse response = meetingUseCase.create(principal.getId(), request);
        return ApiResponse.apiResponseSuccess("Create meeting succeeded", response);
    }

    @PutMapping("/{id}")
    public ApiResponse<MeetingResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid MeetingUpdateRequest request,
                                               Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        MeetingResponse response = meetingUseCase.update(id, principal.getId(), request);
        return ApiResponse.apiResponseSuccess("Update meeting succeeded", response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> delete(@PathVariable Long id, Authentication authentication) {
        AuthenticatedUser principal = getPrincipal(authentication);
        meetingUseCase.delete(id, principal.getId());
        return ApiResponse.apiResponseSuccess("Delete meeting succeeded", null);
    }

    private AuthenticatedUser getPrincipal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser principal)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return principal;
    }
}
