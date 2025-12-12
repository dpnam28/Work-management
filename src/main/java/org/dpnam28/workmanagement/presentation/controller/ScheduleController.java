package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.presentation.dto.schedule.ScheduleRequest;
import org.dpnam28.workmanagement.presentation.dto.schedule.ScheduleResponse;
import org.dpnam28.workmanagement.usecase.ScheduleUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class ScheduleController {

    private final ScheduleUseCase scheduleUseCase;

    @PostMapping
    public ApiResponse<ScheduleResponse> create(@RequestBody @Valid ScheduleRequest request) {
        return ApiResponse.apiResponseSuccess("Create schedule succeeded", scheduleUseCase.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ScheduleResponse> update(@PathVariable Long id,
                                                @RequestBody @Valid ScheduleRequest request) {
        return ApiResponse.apiResponseSuccess("Update schedule succeeded", scheduleUseCase.update(id, request));
    }

    @GetMapping
    public ApiResponse<List<ScheduleResponse>> getAll() {
        return ApiResponse.apiResponseSuccess("Get schedules succeeded", scheduleUseCase.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ScheduleResponse> getById(@PathVariable Long id) {
        return ApiResponse.apiResponseSuccess("Get schedule succeeded", scheduleUseCase.getById(id));
    }
}
