package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Subject;
import org.dpnam28.workmanagement.presentation.dto.subject.SubjectRequest;
import org.dpnam28.workmanagement.presentation.dto.subject.SubjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface SubjectMapper {
    @Mapping(target = "faculty", source = "facultyId")
    Subject toEntity(SubjectRequest request);

    @Mapping(target = "facultyId", source = "faculty.id")
    SubjectResponse toResponse(Subject subject);
}
