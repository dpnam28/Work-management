package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.Report;
import org.dpnam28.workmanagement.presentation.dto.report.ReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(target = "planId", source = "plan.id")
    @Mapping(target = "createdById", source = "createdBy.id")
    ReportResponse toResponse(Report report);

    List<ReportResponse> toResponseList(List<Report> reports);
}
