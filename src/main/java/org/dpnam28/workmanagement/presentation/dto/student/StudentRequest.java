package org.dpnam28.workmanagement.presentation.dto.student;

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
public class StudentRequest {
    @NotNull(message = "User is required")
    private Long userId;

    @NotBlank(message = "Student code is required")
    private String studentCode;

    @NotNull(message = "Entry year is required")
    private Integer entryYear;

    @NotNull(message = "Major is required")
    private Long majorId;
}
