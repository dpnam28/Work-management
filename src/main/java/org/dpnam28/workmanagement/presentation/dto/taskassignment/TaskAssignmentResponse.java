package org.dpnam28.workmanagement.presentation.dto.taskassignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate assignDate;
}
