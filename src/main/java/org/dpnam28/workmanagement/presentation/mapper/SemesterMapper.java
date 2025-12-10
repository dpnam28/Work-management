package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Semester;
import org.dpnam28.workmanagement.presentation.dto.semester.SemesterRequest;
import org.dpnam28.workmanagement.presentation.dto.semester.SemesterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SemesterMapper {
    Semester toEntity(SemesterRequest request);

    SemesterResponse toResponse(Semester semester);
}
