package org.dpnam28.workmanagement.presentation.dto.faculty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacultyRequest {
    @NotBlank(message = "Faculty name is required")
    private String facultyName;

    @NotBlank(message = "Faculty code is required")
    private String facultyCode;
}
