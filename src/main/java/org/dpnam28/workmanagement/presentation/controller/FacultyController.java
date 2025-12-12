package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.presentation.dto.faculty.FacultyRequest;
import org.dpnam28.workmanagement.presentation.dto.faculty.FacultyResponse;
import org.dpnam28.workmanagement.usecase.FacultyUseCase;
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
@RequestMapping("/faculties")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class FacultyController {

    private final FacultyUseCase facultyUseCase;

    @PostMapping
    public ApiResponse<FacultyResponse> create(@RequestBody @Valid FacultyRequest request) {
        return ApiResponse.apiResponseSuccess("Create faculty succeeded", facultyUseCase.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<FacultyResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid FacultyRequest request) {
        return ApiResponse.apiResponseSuccess("Update faculty succeeded", facultyUseCase.update(id, request));
    }

    @GetMapping
    public ApiResponse<List<FacultyResponse>> getAll() {
        return ApiResponse.apiResponseSuccess("Get faculties succeeded", facultyUseCase.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<FacultyResponse> getById(@PathVariable Long id) {
        return ApiResponse.apiResponseSuccess("Get faculty succeeded", facultyUseCase.getById(id));
    }
}
