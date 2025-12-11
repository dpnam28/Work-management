package org.dpnam28.workmanagement.presentation.dto.classsubject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectStudentResponse {
    private Long studentId;
    private String studentCode;
    private String fullName;
    private String email;
    private Integer entryYear;
}
