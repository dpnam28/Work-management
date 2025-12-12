package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.domain.entity.Subject;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.FacultyJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.SubjectJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.subject.SubjectRequest;
import org.dpnam28.workmanagement.presentation.dto.subject.SubjectResponse;
import org.dpnam28.workmanagement.presentation.mapper.SubjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectUseCase {

    private final SubjectJpaRepository subjectRepository;
    private final FacultyJpaRepository facultyRepository;
    private final SubjectMapper subjectMapper;

    public SubjectResponse create(SubjectRequest request) {
        if (subjectRepository.existsBySubjectName(request.getSubjectName())) {
            throw new AppException(ErrorCode.SUBJECT_NAME_ALREADY_EXISTS);
        }
        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
        Subject subject = subjectMapper.toEntity(request);
        subject.setFaculty(faculty);
        return subjectMapper.toResponse(subjectRepository.save(subject));
    }

    public SubjectResponse update(Long id, SubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
        if (!subject.getSubjectName().equalsIgnoreCase(request.getSubjectName())
                && subjectRepository.existsBySubjectName(request.getSubjectName())) {
            throw new AppException(ErrorCode.SUBJECT_NAME_ALREADY_EXISTS);
        }
        subject.setFaculty(faculty);
        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());
        subject.setCredits(request.getCredits());
        return subjectMapper.toResponse(subjectRepository.save(subject));
    }

    public List<SubjectResponse> getAll() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toResponse)
                .toList();
    }

    public SubjectResponse getById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
        return subjectMapper.toResponse(subject);
    }
}
