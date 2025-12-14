package org.dpnam28.workmanagement.usecase;

import lombok.RequiredArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.Plan;
import org.dpnam28.workmanagement.domain.entity.Task;
import org.dpnam28.workmanagement.domain.entity.TaskStatus;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.exception.AppException;
import org.dpnam28.workmanagement.domain.exception.ErrorCode;
import org.dpnam28.workmanagement.infrastructure.repository.PlanJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TaskJpaRepository;
import org.dpnam28.workmanagement.infrastructure.repository.TeacherJpaRepository;
import org.dpnam28.workmanagement.presentation.dto.task.TaskRequest;
import org.dpnam28.workmanagement.presentation.dto.task.TaskResponse;
import org.dpnam28.workmanagement.presentation.dto.task.TaskUpdateRequest;
import org.dpnam28.workmanagement.presentation.mapper.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TaskUseCase {

    private final TaskJpaRepository taskRepository;
    private final PlanJpaRepository planRepository;
    private final TeacherJpaRepository teacherRepository;
    private final TaskMapper taskMapper;

    public TaskResponse create(Long requesterId, TaskRequest request, MultipartFile file) {
        requireTeacher(requesterId);
        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));
        Task task = new Task();
        task.setPlan(plan);
        task.setTaskTitle(request.getTaskTitle());
        task.setDeadline(request.getDeadline());
        task.setTaskDescription(request.getTaskDescription());
        task.setFile(extractPdfBytes(file));
        task.setStatus(TaskStatus.INCOMPLETE);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse update(Long taskId, Long requesterId, TaskUpdateRequest request, MultipartFile file) {
        requireTeacher(requesterId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));
        task.setTaskTitle(request.getTaskTitle());
        task.setDeadline(request.getDeadline());
        task.setTaskDescription(request.getTaskDescription());
        byte[] fileBytes = extractPdfBytes(file);
        if (fileBytes != null) {
            task.setFile(fileBytes);
        }
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse markAsDone(Long taskId, Long requesterId) {
        requireTeacher(requesterId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));
        task.setStatus(TaskStatus.COMPLETED);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    private Teacher requireTeacher(Long teacherId) {
        if (teacherId == null) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_FOUND));
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
