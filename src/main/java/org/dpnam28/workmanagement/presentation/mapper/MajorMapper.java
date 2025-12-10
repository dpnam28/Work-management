package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Major;
import org.dpnam28.workmanagement.presentation.dto.major.MajorRequest;
import org.dpnam28.workmanagement.presentation.dto.major.MajorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface MajorMapper {
    @Mapping(target = "faculty", source = "facultyId")
    Major toEntity(MajorRequest request);

    @Mapping(target = "facultyId", source = "faculty.id")
    MajorResponse toResponse(Major major);
}
