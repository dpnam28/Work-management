package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Plan;
import org.dpnam28.workmanagement.presentation.dto.plan.PlanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "approvedById", source = "approvedBy.id")
    @Mapping(target = "facultyId", source = "faculty.id")
    PlanResponse toResponse(Plan plan);

    List<PlanResponse> toResponseList(List<Plan> plans);
}
