package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Task;
import org.dpnam28.workmanagement.presentation.dto.task.TaskRequest;
import org.dpnam28.workmanagement.presentation.dto.task.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface TaskMapper {
    @Mapping(target = "plan", source = "planId")
    Task toEntity(TaskRequest request);

    @Mapping(target = "planId", source = "plan.id")
    TaskResponse toResponse(Task task);
}
