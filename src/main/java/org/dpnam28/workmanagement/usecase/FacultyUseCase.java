package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.FacultyJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.faculty.FacultyRequest;
import org.dpnam28.workmanagement.presentation.dto.faculty.FacultyResponse;
import org.dpnam28.workmanagement.presentation.mapper.FacultyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyUseCase {

    private final FacultyJpaRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyResponse create(FacultyRequest request) {
        if (facultyRepository.existsByFacultyName(request.getFacultyName())) {
            throw new AppException(ErrorCode.FACULTY_NAME_ALREADY_EXISTS);
        }
        Faculty faculty = facultyMapper.toEntity(request);
        return facultyMapper.toResponse(facultyRepository.save(faculty));
    }

    public FacultyResponse update(Long id, FacultyRequest request) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
        if (!faculty.getFacultyName().equalsIgnoreCase(request.getFacultyName())
                && facultyRepository.existsByFacultyName(request.getFacultyName())) {
            throw new AppException(ErrorCode.FACULTY_NAME_ALREADY_EXISTS);
        }
        faculty.setFacultyName(request.getFacultyName());
        faculty.setFacultyCode(request.getFacultyCode());
        return facultyMapper.toResponse(facultyRepository.save(faculty));
    }

    public List<FacultyResponse> getAll() {
        return facultyRepository.findAll().stream()
                .map(facultyMapper::toResponse)
                .toList();
    }

    public FacultyResponse getById(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
        return facultyMapper.toResponse(faculty);
    }
}
