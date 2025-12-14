package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.TaskAssignment;
import org.dpnam28.workmanagement.presentation.dto.taskassignment.TaskAssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface TaskAssignmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "assignById", source = "assignBy.id")
    @Mapping(target = "assignedToTeacherId", source = "assignedToTeacher.id")
    @Mapping(target = "assignedToFacultyId", source = "assignedToFaculty.id")
    @Mapping(target = "task", source = "task")
    TaskAssignmentResponse toResponse(TaskAssignment assignment);

    List<TaskAssignmentResponse> toResponseList(List<TaskAssignment> assignments);
}
