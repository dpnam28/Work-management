package org.dpnam28.workmanagement.presentation.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.PositionType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequest {
    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Faculty is required")
    private Long facultyId;

    @NotBlank(message = "Teacher code is required")
    private String teacherCode;

    @NotNull(message = "Position is required")
    private PositionType position;
}
