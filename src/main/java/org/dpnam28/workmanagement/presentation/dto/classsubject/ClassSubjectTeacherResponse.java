package org.dpnam28.workmanagement.presentation.dto.classsubject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dpnam28.workmanagement.domain.entity.PositionType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectTeacherResponse {
    private Long teacherId;
    private String teacherCode;
    private String fullName;
    private PositionType position;
}
