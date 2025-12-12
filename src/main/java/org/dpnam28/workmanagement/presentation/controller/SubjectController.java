package org.dpnam28.workmanagement.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.dto.ApiResponse;
import org.dpnam28.workmanagement.presentation.dto.subject.SubjectRequest;
import org.dpnam28.workmanagement.presentation.dto.subject.SubjectResponse;
import org.dpnam28.workmanagement.usecase.SubjectUseCase;
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
@RequestMapping("/subjects")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class SubjectController {

    private final SubjectUseCase subjectUseCase;

    @PostMapping
    public ApiResponse<SubjectResponse> create(@RequestBody @Valid SubjectRequest request) {
        return ApiResponse.apiResponseSuccess("Create subject succeeded", subjectUseCase.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<SubjectResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid SubjectRequest request) {
        return ApiResponse.apiResponseSuccess("Update subject succeeded", subjectUseCase.update(id, request));
    }

    @GetMapping
    public ApiResponse<List<SubjectResponse>> getAll() {
        return ApiResponse.apiResponseSuccess("Get subjects succeeded", subjectUseCase.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<SubjectResponse> getById(@PathVariable Long id) {
        return ApiResponse.apiResponseSuccess("Get subject succeeded", subjectUseCase.getById(id));
    }
}
