package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Plan;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanRequest;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface PlanMapper {
    @Mapping(target = "createdBy", source = "createdById")
    @Mapping(target = "approvedBy", source = "approvedById")
    @Mapping(target = "faculty", source = "facultyId")
    Plan toEntity(PlanRequest request);

    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "approvedById", source = "approvedBy.id")
    @Mapping(target = "facultyId", source = "faculty.id")
    PlanResponse toResponse(Plan plan);
}
