package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.domain.entity.PositionType;
import org.dpnam28.workmanagement.domain.entity.Task;
import org.dpnam28.workmanagement.domain.entity.TaskAssignment;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.FacultyJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TaskAssignmentJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TaskJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeacherJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.taskassignment.TaskAssignmentRequest;
import org.dpnam28.workmanagement.presentation.dto.taskassignment.TaskAssignmentResponse;
import org.dpnam28.workmanagement.presentation.mapper.TaskAssignmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskAssignmentUseCase {

    private final TaskAssignmentJpaRepository assignmentRepository;
    private final TaskJpaRepository taskRepository;
    private final TeacherJpaRepository teacherRepository;
    private final FacultyJpaRepository facultyRepository;
    private final TaskAssignmentMapper assignmentMapper;

    public TaskAssignmentResponse assign(Long requesterId, TaskAssignmentRequest request) {
        Teacher assigner = requireHeadTeacher(requesterId);
        validateAssignmentTarget(request);
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));
        TaskAssignment assignment = new TaskAssignment();
        assignment.setTask(task);
        assignment.setAssignBy(assigner);
        assignment.setAssignDate(LocalDateTime.now());
        if (request.getAssignedToTeacherId() != null) {
            Teacher assignee = teacherRepository.findById(request.getAssignedToTeacherId())
                    .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));
            assignment.setAssignedToTeacher(assignee);
        } else {
            Faculty faculty = facultyRepository.findById(request.getAssignedToFacultyId())
                    .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
            assignment.setAssignedToFaculty(faculty);
        }
        return assignmentMapper.toResponse(assignmentRepository.save(assignment));
    }

    @Transactional(readOnly = true)
    public List<TaskAssignmentResponse> getTeacherAssignments(Long teacherId) {
        Teacher teacher = requireTeacher(teacherId);
        return assignmentMapper.toResponseList(
                assignmentRepository.findByAssignedToTeacher_Id(teacher.getId())
        );
    }

    @Transactional(readOnly = true)
    public List<TaskAssignmentResponse> getFacultyAssignments(Long requesterId, Long facultyId) {
        requireHeadTeacher(requesterId);
        facultyRepository.findById(facultyId)
                .orElseThrow(() -> new AppException(ErrorCode.FACULTY_NOT_FOUND));
        return assignmentMapper.toResponseList(
                assignmentRepository.findByAssignedToFaculty_Id(facultyId)
        );
    }

    @Transactional(readOnly = true)
    public List<TaskAssignmentResponse> getAllAssignments(Long requesterId) {
        requireHeadTeacher(requesterId);
        return assignmentMapper.toResponseList(assignmentRepository.findAll());
    }

    public void cancel(Long requesterId, Long assignmentId) {
        requireHeadTeacher(requesterId);
        TaskAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_ASSIGNMENT_NOT_FOUND));
        assignmentRepository.delete(assignment);
    }

    private void validateAssignmentTarget(TaskAssignmentRequest request) {
        boolean hasTeacher = request.getAssignedToTeacherId() != null;
        boolean hasFaculty = request.getAssignedToFacultyId() != null;
        if (hasTeacher == hasFaculty) {
            throw new AppException(ErrorCode.ASSIGNMENT_TARGET_INVALID);
        }
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
}
