package org.dpnam28.workmanagement.presentation.dto.enrollment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequest {
    @NotNull(message = "Student is required")
    private Long studentId;

    @NotNull(message = "Subject is required")
    private Long subjectId;
}
