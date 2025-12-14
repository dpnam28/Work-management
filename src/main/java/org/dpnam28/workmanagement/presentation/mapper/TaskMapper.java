package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Task;
import org.dpnam28.workmanagement.presentation.dto.task.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "planId", source = "plan.id")
    TaskResponse toResponse(Task task);

    List<TaskResponse> toResponseList(List<Task> tasks);
}
