package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Plan;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.Report;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.PlanJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.ReportJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeacherJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.report.ReportRequest;
import org.dpnam28.workmanagement.presentation.dto.report.ReportResponse;
import org.dpnam28.workmanagement.presentation.dto.report.ReportUpdateRequest;
import org.dpnam28.workmanagement.presentation.mapper.ReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportUseCase {

    private final ReportJpaRepository reportRepository;
    private final PlanJpaRepository planRepository;
    private final TeacherJpaRepository teacherRepository;
    private final ReportMapper reportMapper;

    public ReportResponse create(Long requesterId, ReportRequest request, MultipartFile file) {
        Teacher teacher = requireTeacher(requesterId);
        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));
        Report report = new Report();
        report.setPlan(plan);
        report.setCreatedBy(teacher);
        report.setReportTitle(request.getReportTitle());
        report.setReportContent(request.getReportContent());
        report.setFile(extractPdfBytes(file));
        report.setCreatedDate(LocalDateTime.now());
        return reportMapper.toResponse(reportRepository.save(report));
    }

    public ReportResponse update(Long reportId, Long requesterId, ReportUpdateRequest request, MultipartFile file) {
        Teacher teacher = requireTeacher(requesterId);
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
        ensureOwnerOrHead(report, teacher);
        report.setReportTitle(request.getReportTitle());
        report.setReportContent(request.getReportContent());
        byte[] fileBytes = extractPdfBytes(file);
        if (fileBytes != null) {
            report.setFile(fileBytes);
        }
        return reportMapper.toResponse(reportRepository.save(report));
    }

    public void delete(Long reportId, Long requesterId) {
        Teacher teacher = requireTeacher(requesterId);
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
        ensureOwnerOrHead(report, teacher);
        reportRepository.delete(report);
    }

    public List<ReportResponse> getAll(Long requesterId) {
        requireHeadTeacher(requesterId);
        return reportMapper.toResponseList(reportRepository.findAll());
    }

    private Teacher requireTeacher(Long teacherId) {
        if (teacherId == null) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));
    }

    private void ensureOwnerOrHead(Report report, Teacher teacher) {
        Long ownerId = report.getCreatedBy() != null ? report.getCreatedBy().getId() : null;
        boolean isOwner = ownerId != null && ownerId.equals(teacher.getId());
        boolean isHead = teacher.getPosition() == PositionType.HEAD;
        if (!isOwner && !isHead) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void requireHeadTeacher(Long teacherId) {
        Teacher teacher = requireTeacher(teacherId);
        if (teacher.getPosition() != PositionType.HEAD) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
    }

    private byte[] extractPdfBytes(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.toLowerCase().endsWith(".pdf")) {
            throw new AppException(ErrorCode.FILE_FORMAT_INVALID);
        }
        try {
            return file.getBytes();
        } catch (IOException ex) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
