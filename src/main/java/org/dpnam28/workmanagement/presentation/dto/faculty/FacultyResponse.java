package org.dpnam28.workmanagement.presentation.dto.faculty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacultyResponse {
    private Long id;
    private String facultyName;
    private String facultyCode;
}
