package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.presentation.dto.teacher.TeacherRequest;
import org.dpnam28.workmanagement.presentation.dto.teacher.TeacherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface TeacherMapper {
    @Mapping(target = "id", source = "userId")
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "faculty", source = "facultyId")
    Teacher toEntity(TeacherRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "facultyId", source = "faculty.id")
    TeacherResponse toResponse(Teacher teacher);
}
