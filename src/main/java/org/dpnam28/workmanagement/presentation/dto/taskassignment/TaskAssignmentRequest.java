package org.dpnam28.workmanagement.presentation.dto.taskassignment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignmentRequest {
    @NotNull(message = "Task is required")
    private Long taskId;

    private Long assignedToTeacherId;

    private Long assignedToFacultyId;
}
