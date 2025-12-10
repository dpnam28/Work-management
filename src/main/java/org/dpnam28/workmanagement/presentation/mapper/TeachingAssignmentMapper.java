package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.TeachingAssignment;
import org.dpnam28.workmanagement.domain.entity.TeachingAssignmentId;
import org.dpnam28.workmanagement.presentation.dto.teachingassignment.TeachingAssignmentRequest;
import org.dpnam28.workmanagement.presentation.dto.teachingassignment.TeachingAssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface TeachingAssignmentMapper {

    @Mapping(target = "id", expression = "java(new TeachingAssignmentId(request.getTeacherId(), request.getClassSubjectId()))")
    @Mapping(target = "teacher", source = "teacherId")
    @Mapping(target = "classSubject", source = "classSubjectId")
    TeachingAssignment toEntity(TeachingAssignmentRequest request);

    @Mapping(target = "teacherId", source = "id.teacherId")
    @Mapping(target = "classSubjectId", source = "id.classSubjectId")
    TeachingAssignmentResponse toResponse(TeachingAssignment assignment);
}
