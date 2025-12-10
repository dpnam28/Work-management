package org.dpnam28.workmanagement.presentation.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private Long planId;
    private String taskTitle;
    private String deadline;
    private String taskDescription;
    private String filePath;
    private String status;
}
