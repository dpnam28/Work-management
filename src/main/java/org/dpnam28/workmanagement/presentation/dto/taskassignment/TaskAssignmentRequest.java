package org.dpnam28.workmanagement.presentation.dto.taskassignment;

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
public class TaskAssignmentRequest {
    @NotNull(message = "Task is required")
    private Long taskId;

    private Long assignById;

    private Long assignedToTeacherId;

    private Long assignedToFacultyId;

    @NotNull(message = "Assign date is required")
    private LocalDate assignDate;
}
