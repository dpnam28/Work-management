package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.presentation.dto.faculty.FacultyRequest;
import org.dpnam28.workmanagement.presentation.dto.faculty.FacultyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    Faculty toEntity(FacultyRequest request);

    FacultyResponse toResponse(Faculty faculty);
}
