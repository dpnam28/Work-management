package org.dpnam28.workmanagement.presentation.dto.classsubject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectResponse {
    private Long id;
    private String classCode;
    private Long subjectId;
    private Long semesterId;
    private Integer maxStudent;
}
