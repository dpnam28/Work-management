package org.dpnam28.workmanagement.presentation.dto.taskassignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dpnam28.workmanagement.presentation.dto.task.TaskResponse;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignmentResponse {
    private Long id;
    private Long taskId;
    private Long assignById;
    private Long assignedToTeacherId;
    private Long assignedToFacultyId;
    private LocalDateTime assignDate;
    private TaskResponse task;
}
