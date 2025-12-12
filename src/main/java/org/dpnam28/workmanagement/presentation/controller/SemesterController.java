package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.presentation.dto.semester.SemesterRequest;
import org.dpnam28.workmanagement.presentation.dto.semester.SemesterResponse;
import org.dpnam28.workmanagement.usecase.SemesterUseCase;
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
@RequestMapping("/semesters")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class SemesterController {

    private final SemesterUseCase semesterUseCase;

    @PostMapping
    public ApiResponse<SemesterResponse> create(@RequestBody @Valid SemesterRequest request) {
        return ApiResponse.apiResponseSuccess("Create semester succeeded", semesterUseCase.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<SemesterResponse> update(@PathVariable Long id,
                                                @RequestBody @Valid SemesterRequest request) {
        return ApiResponse.apiResponseSuccess("Update semester succeeded", semesterUseCase.update(id, request));
    }

    @GetMapping
    public ApiResponse<List<SemesterResponse>> getAll() {
        return ApiResponse.apiResponseSuccess("Get semesters succeeded", semesterUseCase.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<SemesterResponse> getById(@PathVariable Long id) {
        return ApiResponse.apiResponseSuccess("Get semester succeeded", semesterUseCase.getById(id));
    }
}
