package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Meeting;
import org.dpnam28.workmanagement.presentation.dto.meeting.MeetingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeetingMapper {

    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "facultyId", source = "faculty.id")
    MeetingResponse toResponse(Meeting meeting);
}
