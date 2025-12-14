package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.domain.entity.Plan;
import org.dpnam28.workmanagement.domain.entity.PlanStatus;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.FacultyJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.PlanJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeacherJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanRequest;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanResponse;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanUpdateRequest;
import org.dpnam28.workmanagement.presentation.mapper.PlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanUseCase {

    private final PlanJpaRepository planRepository;
    private final TeacherJpaRepository teacherRepository;
    private final FacultyJpaRepository facultyRepository;
    private final PlanMapper planMapper;

    public PlanResponse create(Long requesterId, PlanRequest request) {
        Teacher teacher = requireTeacher(requesterId);
        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
        Plan plan = new Plan();
        plan.setPlanTitle(request.getPlanTitle());
        plan.setPlanDescription(request.getPlanDescription());
        plan.setCreatedBy(teacher);
        plan.setFaculty(faculty);
        plan.setStatus(PlanStatus.PENDING);
        return planMapper.toResponse(planRepository.save(plan));
    }

    public PlanResponse update(Long planId, Long requesterId, PlanUpdateRequest request) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));
        Teacher teacher = requireTeacher(requesterId);
        ensureOwnerOrHead(plan, teacher);
        plan.setPlanTitle(request.getPlanTitle());
        plan.setPlanDescription(request.getPlanDescription());
        return planMapper.toResponse(planRepository.save(plan));
    }

    public PlanResponse approve(Long planId, Long requesterId) {
        Teacher head = requireHeadTeacher(requesterId);
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));
        if (plan.getStatus() != PlanStatus.PENDING) {
            throw new AppException(ErrorCode.ENTITY_STATE_INVALID);
        }
        plan.setStatus(PlanStatus.APPROVED);
        plan.setApprovedBy(head);
        return planMapper.toResponse(planRepository.save(plan));
    }

    public PlanResponse reject(Long planId, Long requesterId) {
        Teacher head = requireHeadTeacher(requesterId);
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));
        if (plan.getStatus() != PlanStatus.PENDING) {
            throw new AppException(ErrorCode.ENTITY_STATE_INVALID);
        }
        plan.setStatus(PlanStatus.CANCELED);
        plan.setApprovedBy(head);
        return planMapper.toResponse(planRepository.save(plan));
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getAll(Long requesterId) {
        requireHeadTeacher(requesterId);
        return planMapper.toResponseList(planRepository.findAll());
    }

    private Teacher requireTeacher(Long teacherId) {
        if (teacherId == null) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));
    }

    private Teacher requireHeadTeacher(Long teacherId) {
        Teacher teacher = requireTeacher(teacherId);
        if (teacher.getPosition() != PositionType.HEAD) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return teacher;
    }

    private void ensureOwnerOrHead(Plan plan, Teacher teacher) {
        Long ownerId = plan.getCreatedBy() != null ? plan.getCreatedBy().getId() : null;
        boolean isOwner = ownerId != null && ownerId.equals(teacher.getId());
        boolean isHead = teacher.getPosition() == PositionType.HEAD;
        if (!isOwner && !isHead) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
    }
}
