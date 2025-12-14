package org.dpnam28.workmanagement.presentation.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    @NotNull(message = "Plan is required")
    private Long planId;

    @NotBlank(message = "Report title is required")
    private String reportTitle;

    private String reportContent;
}
