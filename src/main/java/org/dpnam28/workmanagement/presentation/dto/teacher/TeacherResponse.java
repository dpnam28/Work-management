package org.dpnam28.workmanagement.presentation.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.PositionType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {
    private Long id;
    private Long userId;
    private Long facultyId;
    private String teacherCode;
    private PositionType position;
}
