package org.dpnam28.workmanagement.presentation.dto.schedule;

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
public class ScheduleRequest {
    @NotNull(message = "Class subject is required")
    private Long classSubjectId;

    @NotBlank(message = "Time is required")
    private String time;
}
