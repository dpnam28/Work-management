package org.dpnam28.workmanagement.presentation.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private Long planId;
    private String taskTitle;
    private LocalDateTime deadline;
    private String taskDescription;
    private byte[] file;
    private TaskStatus status;
}
