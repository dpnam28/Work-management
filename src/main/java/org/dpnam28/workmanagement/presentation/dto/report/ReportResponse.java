package org.dpnam28.workmanagement.presentation.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private Long id;
    private Long planId;
    private String reportTitle;
    private Long createdById;
    private String reportContent;
    private LocalDate createdDate;
}
