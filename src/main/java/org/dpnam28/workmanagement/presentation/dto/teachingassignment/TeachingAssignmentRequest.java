package org.dpnam28.workmanagement.presentation.dto.teachingassignment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeachingAssignmentRequest {
    @NotNull(message = "Teacher is required")
    private Long teacherId;

    @NotNull(message = "Class subject is required")
    private Long classSubjectId;
}
