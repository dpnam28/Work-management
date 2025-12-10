package org.dpnam28.workmanagement.presentation.dto.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private Long userId;
    private String studentCode;
    private Integer entryYear;
    private Long majorId;
}
