package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.ClassSubject;
import org.dpnam28.workmanagement.presentation.dto.classsubject.ClassSubjectRequest;
import org.dpnam28.workmanagement.presentation.dto.classsubject.ClassSubjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface ClassSubjectMapper {
    @Mapping(target = "subject", source = "subjectId")
    @Mapping(target = "semester", source = "semesterId")
    ClassSubject toEntity(ClassSubjectRequest request);

    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "semesterId", source = "semester.id")
    ClassSubjectResponse toResponse(ClassSubject classSubject);
}
