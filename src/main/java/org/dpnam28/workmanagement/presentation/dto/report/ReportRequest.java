package org.dpnam28.workmanagement.presentation.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    @NotNull(message = "Plan is required")
    private Long planId;

    @NotBlank(message = "Report title is required")
    private String reportTitle;

    @NotNull(message = "Created by is required")
    private Long createdById;

    private String reportContent;

    @NotNull(message = "Created date is required")
    private LocalDate createdDate;
}
