package org.dpnam28.workmanagement.presentation.dto.plan;

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
public class PlanRequest {
    @NotBlank(message = "Plan title is required")
    private String planTitle;

    private String planDescription;

    @NotNull(message = "Created by is required")
    private Long createdById;

    private Long approvedById;

    @NotNull(message = "Faculty is required")
    private Long facultyId;

    @NotBlank(message = "Status is required")
    private String status;
}
