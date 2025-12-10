package org.dpnam28.workmanagement.presentation.dto.major;

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
public class MajorRequest {
    @NotBlank(message = "Major name is required")
    private String majorName;

    @NotBlank(message = "Major code is required")
    private String majorCode;

    @NotNull(message = "Faculty is required")
    private Long facultyId;
}
