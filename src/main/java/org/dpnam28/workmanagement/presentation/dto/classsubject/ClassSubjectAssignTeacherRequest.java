package org.dpnam28.workmanagement.presentation.dto.classsubject;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectAssignTeacherRequest {
    @NotNull(message = "Teacher id is required")
    private Long teacherId;
}
