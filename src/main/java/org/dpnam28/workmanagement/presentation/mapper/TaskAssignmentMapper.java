package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.TaskAssignment;
import org.dpnam28.workmanagement.presentation.dto.taskassignment.TaskAssignmentRequest;
import org.dpnam28.workmanagement.presentation.dto.taskassignment.TaskAssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface TaskAssignmentMapper {
    @Mapping(target = "task", source = "taskId")
    @Mapping(target = "assignBy", source = "assignById")
    @Mapping(target = "assignedToTeacher", source = "assignedToTeacherId")
    @Mapping(target = "assignedToFaculty", source = "assignedToFacultyId")
    TaskAssignment toEntity(TaskAssignmentRequest request);

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "assignById", source = "assignBy.id")
    @Mapping(target = "assignedToTeacherId", source = "assignedToTeacher.id")
    @Mapping(target = "assignedToFacultyId", source = "assignedToFaculty.id")
    TaskAssignmentResponse toResponse(TaskAssignment assignment);
}
