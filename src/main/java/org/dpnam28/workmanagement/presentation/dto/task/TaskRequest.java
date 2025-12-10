package org.dpnam28.workmanagement.presentation.dto.task;

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
public class TaskRequest {
    @NotNull(message = "Plan is required")
    private Long planId;

    @NotBlank(message = "Task title is required")
    private String taskTitle;

    @NotBlank(message = "Deadline is required")
    private String deadline;

    private String taskDescription;

    private String filePath;

    @NotBlank(message = "Status is required")
    private String status;
}
