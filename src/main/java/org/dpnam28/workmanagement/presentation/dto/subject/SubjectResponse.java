package org.dpnam28.workmanagement.presentation.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private Long id;
    private Long facultyId;
    private String subjectName;
    private String subjectCode;
    private BigDecimal credits;
}
