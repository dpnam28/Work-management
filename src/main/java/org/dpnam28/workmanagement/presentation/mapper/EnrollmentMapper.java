package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Enrollment;
import org.dpnam28.workmanagement.domain.entity.EnrollmentId;
import org.dpnam28.workmanagement.presentation.dto.enrollment.EnrollmentRequest;
import org.dpnam28.workmanagement.presentation.dto.enrollment.EnrollmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface EnrollmentMapper {
    @Mapping(target = "id", expression = "java(new EnrollmentId(request.getStudentId(), request.getSubjectId()))")
    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "subject", source = "subjectId")
    Enrollment toEntity(EnrollmentRequest request);

    @Mapping(target = "studentId", source = "id.studentId")
    @Mapping(target = "subjectId", source = "id.subjectId")
    EnrollmentResponse toResponse(Enrollment enrollment);
}
