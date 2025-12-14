package org.dpnam28.workmanagement.presentation.dto.plan;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanUpdateRequest {

    @NotBlank(message = "Plan title is required")
    private String planTitle;

    private String planDescription;
}
