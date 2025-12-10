package org.dpnam28.workmanagement.presentation.dto.classsubject;

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
public class ClassSubjectRequest {
    @NotBlank(message = "Class code is required")
    private String classCode;

    @NotNull(message = "Subject is required")
    private Long subjectId;

    @NotNull(message = "Semester is required")
    private Long semesterId;

    @NotNull(message = "Max student is required")
    private Integer maxStudent;
}
