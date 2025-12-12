package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Semester;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.SemesterJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.semester.SemesterRequest;
import org.dpnam28.workmanagement.presentation.dto.semester.SemesterResponse;
import org.dpnam28.workmanagement.presentation.mapper.SemesterMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterUseCase {

    private final SemesterJpaRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    public SemesterResponse create(SemesterRequest request) {
        if (semesterRepository.existsBySemesterName(request.getSemesterName())) {
            throw new AppException(ErrorCode.SEMESTER_NAME_ALREADY_EXISTS);
        }
        Semester semester = semesterMapper.toEntity(request);
        return semesterMapper.toResponse(semesterRepository.save(semester));
    }

    public SemesterResponse update(Long id, SemesterRequest request) {
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
        if (!semester.getSemesterName().equalsIgnoreCase(request.getSemesterName())
                && semesterRepository.existsBySemesterName(request.getSemesterName())) {
            throw new AppException(ErrorCode.SEMESTER_NAME_ALREADY_EXISTS);
        }
        semester.setSemesterName(request.getSemesterName());
        semester.setStartDate(request.getStartDate());
        semester.setEndDate(request.getEndDate());
        return semesterMapper.toResponse(semesterRepository.save(semester));
    }

    public List<SemesterResponse> getAll() {
        return semesterRepository.findAll().stream()
                .map(semesterMapper::toResponse)
                .toList();
    }

    public SemesterResponse getById(Long id) {
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
        return semesterMapper.toResponse(semester);
    }
}
