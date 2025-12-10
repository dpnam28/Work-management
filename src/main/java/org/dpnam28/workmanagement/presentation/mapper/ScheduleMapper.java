package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Schedule;
import org.dpnam28.workmanagement.presentation.dto.schedule.ScheduleRequest;
import org.dpnam28.workmanagement.presentation.dto.schedule.ScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface ScheduleMapper {
    @Mapping(target = "classSubject", source = "classSubjectId")
    Schedule toEntity(ScheduleRequest request);

    @Mapping(target = "classSubjectId", source = "classSubject.id")
    ScheduleResponse toResponse(Schedule schedule);
}
