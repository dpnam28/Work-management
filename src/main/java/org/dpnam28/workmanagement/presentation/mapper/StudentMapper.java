package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Student;
import org.dpnam28.workmanagement.presentation.dto.student.StudentRequest;
import org.dpnam28.workmanagement.presentation.dto.student.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface StudentMapper {

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "major", source = "majorId")
    Student toEntity(StudentRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "majorId", source = "major.id")
    StudentResponse toResponse(Student student);
}
