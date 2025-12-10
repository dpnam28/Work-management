package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Report;
import org.dpnam28.workmanagement.presentation.dto.report.ReportRequest;
import org.dpnam28.workmanagement.presentation.dto.report.ReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface ReportMapper {
    @Mapping(target = "plan", source = "planId")
    @Mapping(target = "createdBy", source = "createdById")
    Report toEntity(ReportRequest request);

    @Mapping(target = "planId", source = "plan.id")
    @Mapping(target = "createdById", source = "createdBy.id")
    ReportResponse toResponse(Report report);
}
