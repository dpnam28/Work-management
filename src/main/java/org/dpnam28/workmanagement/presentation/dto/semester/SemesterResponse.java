package org.dpnam28.workmanagement.presentation.dto.semester;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SemesterResponse {
    private Long id;
    private String semesterName;
    private LocalDate startDate;
    private LocalDate endDate;
}
